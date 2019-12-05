// use this to isolate the scope
(function () {

    if(!$axure.document.configuration.showConsole) { return; }

    $(document).ready(function () {
        $axure.player.createPluginHost({
            id: 'debugHost',
            context: 'interface',
            title: 'CONSOLE',
            gid: 3
        });

        generateDebug();

        $('#variablesClearLink').click(clearvars_click);
        $('#traceClearLink').click(cleartrace_click);


        $(document).on('ContainerHeightChange', function () {
            updateContainerHeight();
        });

        //$('#traceContainer').hide();
        //$('#debugTraceLink').click(function () {
        //    $('#variablesContainer').hide();
        //    $('#traceContainer').show();
        //});
        //$('#debugVariablesLink').click(function () {
        //    $('#variablesContainer').show();
        //    $('#traceContainer').hide();
        //});

        var currentStack= [];
        var finishedStack = [];

        $axure.messageCenter.addMessageListener(function (message, data) {
            if(message == 'globalVariableValues') {
                //If variables container isn't visible, then ignore
                //if(!$('#variablesContainer').is(":visible")) {
                //    return;
                //}

                $('#variablesDiv').empty();
                for(var key in data) {
                    var value = data[key] == '' ? '(blank)' : data[key];
                    $('#variablesDiv').append('<div class="variableDiv"><span class="variableName">' + key + '</span><br/>' + value + '</div>');
                }
            } if(message == 'setGlobalVar') {
                //$('#variablesContainer').html("");
                //for (var variable in $axure.globalVariableProvider.getDefinedVariables) {
                //    $('#variablesContainer').append("<div class='varName'>" + variable + "</div>");
                //    $('#variablesContainer').append("<div class='varVal'>" + $axure.globalVariableProvider.getVariableValue(variable) + "</div>");
                //}
            } else if(message == 'axEvent') {
                var addToStack = "<div class='axEventBlock'>";
                addToStack += "<div class='axTime'>" + new Date().toLocaleTimeString() + "</div>";
                addToStack += "<div class='axLabel'>" + data.label + " (" + data.type + ")</div>";
                addToStack += "<div class='axEvent'>" + data.event.description + "</div>";
                currentStack.push(addToStack);
            } else if (message == 'axEventComplete') {
                currentStack[currentStack.length - 1] += "</div>";
                finishedStack.push(currentStack.pop());
                if(currentStack.length == 0) {
                    $('#traceClearLinkContainer').show();
                    $('#traceEmptyState').hide();

                    $('.lastAxEvent').removeClass('lastAxEvent');
                    for(var i = finishedStack.length - 1; i >= 0; i--) {
                        if($('#traceDiv').children().length > 99) $('#traceDiv').children().last().remove();
                        $('#traceDiv').prepend(finishedStack[i]);
                        if(i == finishedStack.length - 1) $('#traceDiv').children().first().addClass('lastAxEvent');
                    }
                    finishedStack = [];
                }
            } else if (message == 'axCase') {
                currentStack[currentStack.length - 1] += "<div class='axCase'>" + data.description + "</div>";
            } else if (message == 'axAction') {
                currentStack[currentStack.length - 1] += "<div class='axAction'>" + data.description + "</div>";
            }
        });

        // bind to the page load
        $axure.page.bind('load.debug', function () {

            $axure.messageCenter.postMessage('getGlobalVariables', '');

            return false;
        });

        function clearvars_click(event) {
            $axure.messageCenter.postMessage('resetGlobalVariables', '');
        }

        function cleartrace_click(event) {
            $('#traceDiv').html('');
            $('#traceClearLinkContainer').hide();
            $('#traceEmptyState').show();
        }
    });

    function updateContainerHeight() {
        $('#debugScrollContainer').height($('#debugHost').height() - $('#debugHeader').outerHeight());
    }

    function generateDebug() {
        var pageNotesUi = "<div id='debugHeader'' class='sitemapHeader'>";

        pageNotesUi += "<div id='debugToolbar' class='sitemapToolbar'>";
        pageNotesUi += "<div class='pluginNameHeader'>CONSOLE</div>";
        pageNotesUi += "<div class='pageNameHeader'></div>";

        //pageNotesUi += "<div class='pageButtonHeader'>";

        //pageNotesUi += "<a id='previousPageButton' title='Previous Page' class='sitemapToolbarButton'></a>";
        //pageNotesUi += "<a id='nextPageButton' title='Next Page' class='sitemapToolbarButton'></a>";
        //pageNotesUi += "<a id='variablesClearLink' title='Reset Variables' class='sitemapToolbarButton'></a>";

        //pageNotesUi += "</div>";
        pageNotesUi += "</div>";
        pageNotesUi += "</div>";

        //var pageNotesUi = "<div id='debugToolbar'><a id='debugVariablesLink' class='debugToolbarButton'>Variables</a> | <a id='debugTraceLink' class='debugToolbarButton'>Trace</a></div>";
        pageNotesUi += "<div id='debugScrollContainer'>";
        pageNotesUi += "<div id='debugContainer'>";
        pageNotesUi += "<div id='variablesContainer'>";
        pageNotesUi += "<div id='variablesClearLinkContainer' class='debugLinksContainer'><a id='variablesClearLink' title='Reset Variables'>Reset Variables</a></div>";
        pageNotesUi += "<div id='variablesDiv'></div></div>";
        pageNotesUi += "<div class='dottedDivider'></div>";
        pageNotesUi += "<div id='traceContainer'>";
        pageNotesUi += "<div id='traceClearLinkContainer' class='debugLinksContainer'><a id='traceClearLink' title='Clear Trace'>Clear Trace</a></div>";
        pageNotesUi += "<div id='traceEmptyState' class='emptyStateContainer'><div class='emptyStateTitle'>No interactions in the trace.</div><div class='emptyStateContent'>Triggered interactions will appear here.</div><div class='dottedDivider'></div></div>";
        pageNotesUi += "<div id='traceDiv'></div></div>";
        pageNotesUi += "</div></div>";

        $('#debugHost').html(pageNotesUi);
        updateContainerHeight();

        $('#traceClearLinkContainer').hide();
        $('#traceEmptyState').show();
    }

})();   