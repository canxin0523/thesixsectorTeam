var currentNodeUrl = '';
var allNodeUrls = [];

function openNextPage() {
    var index = allNodeUrls.indexOf(currentNodeUrl) + 1;
    if(index >= allNodeUrls.length) return;
    var nextNodeUrl = allNodeUrls[index];
    $('.sitemapPageLink[nodeUrl="' + nextNodeUrl + '"]').click();
}

function openPreviousPage() {
    var index = allNodeUrls.indexOf(currentNodeUrl) - 1;
    if(index < 0) return;
    var nextNodeUrl = allNodeUrls[index];
    $('.sitemapPageLink[nodeUrl="' + nextNodeUrl + '"]').click();
}

// use this to isolate the scope
(function() {

    var SHOW_HIDE_ANIMATION_DURATION = 0;

    var HIGHLIGHT_INTERACTIVE_VAR_NAME = 'hi';
    
    var currentPageLoc = '';
    var currentPlayerLoc = '';
    var currentPageHashString = '';

    $(window.document).ready(function() {
        $axure.player.createPluginHost({
            id: 'sitemapHost',
            context: 'interface',
            title: 'PAGES',
            gid: 1
        });

        generateSitemap();

        $('.sitemapPlusMinusLink').toggle(collapse_click, expand_click);
        $('.sitemapPageLink').click(node_click);

        $('#sitemapLinksContainer').hide();
        $('#linksButton').click(links_click);
        $('#adaptiveButton').click(adaptive_click);
        $('#adaptiveViewsContainer').hide();

        $('#highlightInteractiveButton').click(highlight_interactive);
        $('#searchButton').click(search_click);
        $('#searchBox').keyup(search_input_keyup);
        $('.sitemapLinkField').click(function() { this.select(); });
        $('input[value="withoutmap"]').click(withoutSitemapRadio_click);
        $('input[value="withmap"]').click(withSitemapRadio_click);
        $('#minimizeBox, #collapseBox, #footnotesBox, #highlightBox').change(sitemapUrlOptions_change);
        $('#viewSelect').change(sitemapUrlViewSelect_change);

        $(document).on('ContainerHeightChange', function() {
            updateContainerHeight();
        });

        // bind to the page load
        $axure.page.bind('load.sitemap', function() {
            currentPageLoc = $axure.page.location.split("#")[0];
            var decodedPageLoc = decodeURI(currentPageLoc);
            currentNodeUrl = decodedPageLoc.substr(decodedPageLoc.lastIndexOf('/') ? decodedPageLoc.lastIndexOf('/') + 1 : 0);
            currentPlayerLoc = $(location).attr('href').split("#")[0].split("?")[0];
            currentPageHashString = '#p=' + currentNodeUrl.substr(0, currentNodeUrl.lastIndexOf('.'));

            setVarInCurrentUrlHash('p', currentNodeUrl.substring(0, currentNodeUrl.lastIndexOf('.html')));

            $('.sitemapPageLink').parent().parent().removeClass('sitemapHighlight');
            $('.sitemapPageLink[nodeUrl="' + currentNodeUrl + '"]').parent().parent().addClass('sitemapHighlight');

            var pageName = $axure.page.pageName;
            $('.pageNameHeader').html(pageName);

            $('#sitemapLinksPageName').html($('.sitemapHighlight > .sitemapPageLinkContainer > .sitemapPageLink > .sitemapPageName').html());

            //Click the "Without sitemap" radio button so that it's selected by default
            $('input[value="withoutmap"]').click();

            //If highlight var is present and set to 1 or else if
            //sitemap highlight button is selected then highlight interactive elements
            var hiVal = getHashStringVar(HIGHLIGHT_INTERACTIVE_VAR_NAME);
            if(hiVal.length > 0 && hiVal == 1) {
                $('#highlightInteractiveButton').addClass('sitemapToolbarButtonSelected');
                $axure.messageCenter.postMessage('highlightInteractive', true);
            } else if($('#highlightInteractiveButton').is('.sitemapToolbarButtonSelected')) {
                $axure.messageCenter.postMessage('highlightInteractive', true);
            }

            //Set the current view if it is defined in the hash string
            //If the view is invalid, set it to 'auto' in the string
            //ELSE set the view based on the currently selected view in the toolbar menu
            var viewStr = getHashStringVar(ADAPTIVE_VIEW_VAR_NAME);
            if(viewStr.length > 0) {
                var $view = $('.adaptiveViewOption[val="' + viewStr + '"]');
                if($view.length > 0) $view.click();
                else $('.adaptiveViewOption[val="auto"]').click();
            } else if($('.checkedAdaptive').length > 0) {
                var $viewOption = $('.checkedAdaptive').parents('.adaptiveViewOption');
                if($viewOption.attr('val') != 'auto') $viewOption.click();
            }

            $axure.messageCenter.postMessage('finishInit');

            return false;
        });

        var $adaptiveViewsContainer = $('#adaptiveViewsContainer');
        var $viewSelect = $('#viewSelect');

        //Fill out adaptive view container with prototype's defined adaptive views, as well as the default, and Auto
        $adaptiveViewsContainer.append('<div class="adaptiveViewOption" val="auto"><div class="adaptiveCheckboxDiv checkedAdaptive"></div>Auto</div>');
        $viewSelect.append('<option value="auto">Auto</option>');
        if(typeof $axure.document.defaultAdaptiveView.name != 'undefined') {
            //If the name is a blank string, make the view name the width if non-zero, else 'any'
            var defaultViewName = $axure.document.defaultAdaptiveView.name;            
            $adaptiveViewsContainer.append('<div class="adaptiveViewOption currentAdaptiveView" val="default"><div class="adaptiveCheckboxDiv"></div>' + defaultViewName + '</div>');
            $viewSelect.append('<option value="default">' + defaultViewName + '</option>');
        }

        var enabledViewIds = $axure.document.configuration.enabledViewIds;
        for(var viewIndex = 0; viewIndex < $axure.document.adaptiveViews.length; viewIndex++) {
            var currView = $axure.document.adaptiveViews[viewIndex];
            if(enabledViewIds.indexOf(currView.id) < 0) continue;

            var widthString = currView.size.width == 0 ? 'any' : currView.size.width;
            var heightString = currView.size.height == 0 ? 'any' : currView.size.height;
            var conditionString = '';
            if(currView.condition == '>' || currView.condition == '>=') {
                conditionString = ' and above';
            } else if(currView.condition == '<' || currView.condition == '<=') {
                conditionString = ' and below';
            }

            var viewString = currView.name + ' (' + widthString + ' x ' + heightString + conditionString + ')';
            $adaptiveViewsContainer.append('<div class="adaptiveViewOption" val="' + currView.id + '"><div class="adaptiveCheckboxDiv"></div>' + viewString + '</div>');
            $viewSelect.append('<option value="' + currView.id + '">' + viewString + '</option>');
        }

        $('.adaptiveViewOption').click(adaptiveViewOption_click);

        $('.adaptiveViewOption').mouseup(function(event) {
            event.stopPropagation();
        });

        $('#searchBox').focusin(function() {
            if($(this).is('.searchBoxHint')) {
                $(this).val('');
                $(this).removeClass('searchBoxHint');
            }
        }).focusout(function() {
            if($(this).val() == '') {
                $(this).addClass('searchBoxHint');
                $(this).val('Search');
            }
        });


        $('#searchBox').focusout();
    });

    function updateContainerHeight() {
        $('#sitemapTreeContainer').height($('#sitemapHost').height() - $('#sitemapHeader').outerHeight());
    }

    function hideAllContainersExcept(exceptContainer) {
        //1 - adaptive container, 3 - links container
        if(exceptContainer != 1) {
            $('#adaptiveViewsContainer').hide();
            $('#adaptiveButton').removeClass('sitemapToolbarButtonSelected');
        }
        if(exceptContainer != 3) {
            $('#sitemapLinksContainer').hide();
            $('#linksButton').removeClass('sitemapToolbarButtonSelected');
        }
    }
    
    function collapse_click(event) {
        $(this)
            .children('.sitemapMinus').removeClass('sitemapMinus').addClass('sitemapPlus').end()
            .closest('li').children('ul').hide(SHOW_HIDE_ANIMATION_DURATION);

        $(this).next('.sitemapFolderOpenIcon').removeClass('sitemapFolderOpenIcon').addClass('sitemapFolderIcon');
    }

    function expand_click(event) {
        $(this)
            .children('.sitemapPlus').removeClass('sitemapPlus').addClass('sitemapMinus').end()
            .closest('li').children('ul').show(SHOW_HIDE_ANIMATION_DURATION);

        $(this).next('.sitemapFolderIcon').removeClass('sitemapFolderIcon').addClass('sitemapFolderOpenIcon');
    }

    function node_click(event) {
        $axure.page.navigate(this.getAttribute('nodeUrl'), true);
    }

    function links_click(event) {
        hideAllContainersExcept(3);
        $('#sitemapLinksContainer').toggle();
        updateContainerHeight();
        if($('#sitemapLinksContainer').is(":visible")) {
            $('#linksButton').addClass('sitemapToolbarButtonSelected');
        } else {
            $('#linksButton').removeClass('sitemapToolbarButtonSelected');
        }
    }

    $axure.messageCenter.addMessageListener(function(message, data) {
        if(message == 'adaptiveViewChange') {
            $('.adaptiveViewOption').removeClass('currentAdaptiveView');
            if(data.viewId) {$('div[val="' + data.viewId + '"]').addClass('currentAdaptiveView');}
            else $('div[val="default"]').addClass('currentAdaptiveView');

            //when we set adaptive view through user event, we want to update the checkmark on sitemap
            if(data.forceSwitchTo) {
                $('.checkedAdaptive').removeClass('checkedAdaptive');
                $('div[val="' + data.forceSwitchTo + '"]').find('.adaptiveCheckboxDiv').addClass('checkedAdaptive');
            }
        }
    });

    $(document).on('pluginShown', function (event, data) {
        if(data == 1) {
            hideAllContainersExcept(1);
            updateContainerHeight();
        }
    });

    $(document).on('sidebarExpanded', function (event, data) {
        hideAllContainersExcept(1);
        updateContainerHeight();
    });

    function highlight_interactive(event) {
        if($('#highlightInteractiveButton').is('.sitemapToolbarButtonSelected')) {
            $('#highlightInteractiveButton').removeClass('sitemapToolbarButtonSelected');
            $axure.messageCenter.postMessage('highlightInteractive', false);
            //Delete 'hi' hash string var if it exists since default is unselected
            deleteVarFromCurrentUrlHash(HIGHLIGHT_INTERACTIVE_VAR_NAME);
        } else {
            $('#highlightInteractiveButton').addClass('sitemapToolbarButtonSelected');
            $axure.messageCenter.postMessage('highlightInteractive', true);
            //Add 'hi' hash string var so that stay highlighted across reloads
            setVarInCurrentUrlHash(HIGHLIGHT_INTERACTIVE_VAR_NAME, 1);
        }
    }

    function adaptive_click(event) {
        hideAllContainersExcept(1);
        $('#adaptiveViewsContainer').toggle();
        updateContainerHeight();
        if(!$('#adaptiveViewsContainer').is(":visible")) {
            $('#adaptiveButton').removeClass('sitemapToolbarButtonSelected');
        } else {
            $('#adaptiveButton').addClass('sitemapToolbarButtonSelected');
        }
    }

    function adaptiveViewOption_click(event) {
        var currVal = $(this).attr('val');

        $('.checkedAdaptive').removeClass('checkedAdaptive');
        $(this).find('.adaptiveCheckboxDiv').addClass('checkedAdaptive');

        currentPageLoc = $axure.page.location.split("#")[0];
        var decodedPageLoc = decodeURI(currentPageLoc);
        var nodeUrl = decodedPageLoc.substr(decodedPageLoc.lastIndexOf('/') ? decodedPageLoc.lastIndexOf('/') + 1 : 0);
        var adaptiveData = {
            src: nodeUrl
        };

        adaptiveData.view = currVal;
        $axure.messageCenter.postMessage('switchAdaptiveView', adaptiveData);

        if(currVal == 'auto') {
            //Remove view in hash string if one is set
            deleteVarFromCurrentUrlHash(ADAPTIVE_VIEW_VAR_NAME);
        } else {
            //Set current view in hash string so that it can be maintained across reloads
            setVarInCurrentUrlHash(ADAPTIVE_VIEW_VAR_NAME, currVal);
        }
    }

    function search_click(event) {
        $('#searchDiv').toggle();
        if(!$('#searchDiv').is(":visible")) {
            $('#searchButton').removeClass('sitemapToolbarButtonSelected');
            $('#searchBox').val('');
            $('#searchBox').keyup();
            //$('#sitemapToolbar').css('height', '22px');
            $('#sitemapTreeContainer').css('top', '31px');
        } else {
            $('#searchButton').addClass('sitemapToolbarButtonSelected');
            $('#searchBox').focus();
            //$('#sitemapToolbar').css('height', '50px');
            $('#sitemapTreeContainer').css('top', '63px');
        }
    }

    function search_input_keyup(event) {
        var searchVal = $(this).val().toLowerCase();
        //If empty search field, show all nodes, else grey+hide all nodes and
        //ungrey+unhide all matching nodes, as well as unhide their parent nodes
        if(searchVal == '') {
            $('.sitemapPageName').removeClass('sitemapGreyedName');
            $('.sitemapNode').show();
        } else {
            $('.sitemapNode').hide();

            $('.sitemapPageName').addClass('sitemapGreyedName').each(function() {
                var nodeName = $(this).text().toLowerCase();
                if(nodeName.indexOf(searchVal) != -1) {
                    $(this).removeClass('sitemapGreyedName').parents('.sitemapNode:first').show().parents('.sitemapExpandableNode').show();
                }
            });
        }
    }

    function withoutSitemapRadio_click() {
        $('#sitemapLinkWithPlayer').val(currentPageLoc);
        $('#sitemapOptionsDiv').hide();
        $('#minimizeBox').attr('disabled', 'disabled');
        $('#collapseBox').attr('disabled', 'disabled');
        $('#footnotesBox').attr('disabled', 'disabled');
        $('#highlightBox').attr('disabled', 'disabled');
        $('#viewSelect').attr('disabled', 'disabled');
        $('input[value="withmap"]').parent().removeClass('sitemapRadioSelected');

        updateContainerHeight();
    }

    function withSitemapRadio_click() {
        $('#sitemapLinkWithPlayer').val(currentPlayerLoc + currentPageHashString);
        $('#minimizeBox').removeAttr('disabled').change();
        $('#collapseBox').removeAttr('disabled').change();
        $('#footnotesBox').removeAttr('disabled').change();
        $('#highlightBox').removeAttr('disabled').change();
        $('#viewSelect').removeAttr('disabled').change();
        $('#sitemapOptionsDiv').show();
        $('input[value="withmap"]').parent().addClass('sitemapRadioSelected');

        updateContainerHeight();
    }

    function sitemapUrlOptions_change() {
        var currLinkHash = '#' + $('#sitemapLinkWithPlayer').val().split("#")[1];
        var newHash = null;
        var varName = '';
        var defVal = 1;
        if($(this).is('#minimizeBox')) {
            varName = SITEMAP_COLLAPSE_VAR_NAME;
        } else if($(this).is('#collapseBox')) {
            varName = PLUGIN_VAR_NAME;
            defVal = 0;
        } else if($(this).is('#footnotesBox')) {
            varName = FOOTNOTES_VAR_NAME;
            defVal = 0;
        } else if($(this).is('#highlightBox')) {
            varName = HIGHLIGHT_INTERACTIVE_VAR_NAME;
        }

        newHash = $(this).is(':checked') ? setHashStringVar(currLinkHash, varName, defVal) : deleteHashStringVar(currLinkHash, varName);

        if(newHash != null) {
            $('#sitemapLinkWithPlayer').val(currentPlayerLoc + newHash);
        }
    }

    function sitemapUrlViewSelect_change() {
        var currLinkHash = '#' + $('#sitemapLinkWithPlayer').val().split("#")[1];
        var newHash = null;
        var $selectedOption = $(this).find('option:selected');
        if($selectedOption.length == 0) return;
        var selectedVal = $selectedOption.attr('value');

        newHash = selectedVal == 'auto' ? deleteHashStringVar(currLinkHash, ADAPTIVE_VIEW_VAR_NAME) : setHashStringVar(currLinkHash, ADAPTIVE_VIEW_VAR_NAME, selectedVal);

        if(newHash != null) {
            $('#sitemapLinkWithPlayer').val(currentPlayerLoc + newHash);
        }
    }

    function generateSitemap() {
        var treeUl = "<div id='sitemapHeader'' class='sitemapHeader'>";
        treeUl += "<div id='sitemapToolbar' class='sitemapToolbar'>";
        treeUl += "<div class='pluginNameHeader'>PAGES</div>";
        treeUl += "<div class='pageNameHeader'></div>";

        treeUl += "<div class='pageButtonHeader'>";

        if($axure.document.configuration.enabledViewIds.length > 0) {
            treeUl += "<a id='adaptiveButton' title='Select Adaptive View' class='sitemapToolbarButton'></a>";
        }

        treeUl += "<a id='linksButton' title='Get Links' class='sitemapToolbarButton'></a>";
        treeUl += "<a id='highlightInteractiveButton' title='Highlight interactive elements' class='sitemapToolbarButton'></a>";
        treeUl += "</div>";
        
        treeUl += "</div>";

        if($axure.document.adaptiveViews.length > 0) {
            treeUl += "<div id='adaptiveViewsContainer'><div style='margin-bottom:10px;'>Adaptive Views</div></div>";
        }

        //linkcontainer
        treeUl += "<div id='sitemapLinksContainer' class='sitemapLinkContainer'>";
        treeUl += "<div style='margin-bottom:10px;'>Generate sharable URLs</div>";
        treeUl += "<input id='sitemapLinkWithPlayer' type='text' class='sitemapLinkField'/>";
        treeUl += "<div class='sitemapOptionContainer'>";
        treeUl += "<div><label><input type='radio' name='sitemapToggle' value='withoutmap'/>Without Sidebar</label></div>";
        treeUl += "<div style='margin-top:10px;'><label><input type='radio' name='sitemapToggle' value='withmap'/>With Sidebar</label>";

        treeUl += "<div id='sitemapOptionsDiv'>";
        treeUl += "<div class='sitemapUrlOption'><label><input type='checkbox' id='minimizeBox' />Minimize sidebar</label></div>";
        treeUl += "<div class='sitemapUrlOption'><label><input type='checkbox' id='collapseBox' />Pages closed</label></div>";
        if($axure.document.configuration.showAnnotations == true) {
            treeUl += "<div class='sitemapUrlOption'><label><input type='checkbox' id='footnotesBox' />Hide footnotes</label></div>";
        }

        treeUl += "<div class='sitemapUrlOption'><label><input type='checkbox' id='highlightBox' />Highlight interactive elements</label></div>";

        if($axure.document.configuration.enabledViewIds.length > 0) {
            treeUl += "<div id='viewSelectDiv' class='sitemapUrlOption'><label>View: <select id='viewSelect'></select></label></div>";
        }

        treeUl += "</div></div></div></div>";
        /////////////////

        treeUl += "</div>";

        treeUl += "<div id='sitemapTreeContainer'>";

        treeUl += '<div id="searchDiv" style=""><input id="searchBox" style="" type="text"/></div>';

        treeUl += "<ul class='sitemapTree' style='clear:both;'>";
        var rootNodes = $axure.document.sitemap.rootNodes;
        for(var i = 0; i < rootNodes.length; i++) {
            treeUl += generateNode(rootNodes[i], 0);
        }
        treeUl += "</ul></div>";

        $('#sitemapHost').html(treeUl);
        if($axure.document.adaptiveViews.length <= 0) {
            $('#sitemapHost .pageNameHeader').css('padding-right', '55px');
        }
    }

    function generateNode(node, level) {
        var hasChildren = (node.children && node.children.length > 0);
        var margin, returnVal;
        if(hasChildren) {
            margin = (9 + level * 17);
            returnVal = "<li class='sitemapNode sitemapExpandableNode'><div><div class='sitemapPageLinkContainer' style='margin-left:" + margin + "px'><a class='sitemapPlusMinusLink'><span class='sitemapMinus'></span></a>";
        } else {
            margin = (21 + level * 17);
            returnVal = "<li class='sitemapNode sitemapLeafNode'><div><div class='sitemapPageLinkContainer' style='margin-left:" + margin + "px'>";
        }

        var isFolder = node.type == "Folder";
        if(!isFolder) {
            returnVal += "<a class='sitemapPageLink' nodeUrl='" + node.url + "'>";
            allNodeUrls.push(node.url);
        }
        returnVal += "<span class='sitemapPageIcon";
        if(node.type == "Flow") { returnVal += " sitemapFlowIcon"; }
        if(isFolder) {
            if(hasChildren) returnVal += " sitemapFolderOpenIcon";
            else returnVal += " sitemapFolderIcon";
        }

        returnVal += "'></span><span class='sitemapPageName'>";
        returnVal += $('<div/>').text(node.pageName).html();
        returnVal += "</span>";
        if(!isFolder) returnVal += "</a>";
        returnVal += "</div></div>";

        if(hasChildren) {
            returnVal += "<ul>";
            for(var i = 0; i < node.children.length; i++) {
                var child = node.children[i];
                returnVal += generateNode(child, level + 1);
            }
            returnVal += "</ul>";
        }
        returnVal += "</li>";
        return returnVal;
    }
})();
