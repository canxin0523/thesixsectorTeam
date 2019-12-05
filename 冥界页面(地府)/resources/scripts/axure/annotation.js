// ******* Annotation MANAGER ******** //
$axure.internal(function($ax) {
    var NOTE_SIZE = 10;

    var _annotationManager = $ax.annotation = {};

    var _updateLinkLocations = $ax.annotation.updateLinkLocations = function(textId) {
        var diagramObject = $ax.getObjectFromElementId(textId);
        var rotation = (diagramObject && diagramObject.style.rotation);
        var shapeId = $ax.style.GetShapeIdFromText(textId);

        //we have to do this because webkit reports the post-transform position but when you set
        //positions it's pre-transform
        if(WEBKIT && rotation) {
            //we can dynamiclly rotate a widget now, show need to remember the transform rather than just remove it
            //here jquery.css will return 'none' if element is display none
            var oldShapeTransform = document.getElementById(shapeId).style['-webkit-transform'];
            var oldTextTransform = document.getElementById(textId).style['-webkit-transform'];
            $('#' + shapeId).css('-webkit-transform', 'scale(1)');
            $('#' + textId).css('-webkit-transform', 'scale(1)');
        }

        $('#' + textId).find('span[id$="_ann"]').each(function(index, value) {
            var elementId = value.id.replace('_ann', '');

            var annPos = $(value).position();
            var left = annPos.left - NOTE_SIZE;
            var top = annPos.top;

            $('#' + elementId + 'Note').css('left', left).css('top', top);
        });

        //undo the transform reset
        if(WEBKIT && rotation) {
            $('#' + shapeId).css('-webkit-transform', oldShapeTransform || '');
            $('#' + textId).css('-webkit-transform', oldTextTransform || '');
        }
    };

    var dialogs = {};
    $ax.annotation.ToggleWorkflow = function(event, id, width, height) {

        if(dialogs[id]) {
            var $dialog = dialogs[id];
            // reset the dialog
            dialogs[id] = undefined;
            if($dialog.dialog("isOpen")) {
                $dialog.dialog("close");
                return;
            }
        }

        // we'll need to save the scroll position just for stupid IE which will skip otherwise
        var win = $(window);
        var scrollY = win.scrollTop();
        var scrollX = win.scrollLeft();

        var bufferH = 10;
        var bufferV = 10;
        var blnLeft = false;
        var blnAbove = false;
        var sourceTop = event.pageY - scrollY;
        var sourceLeft = event.pageX - scrollX;

        if(sourceLeft > width + bufferH) {
            blnLeft = true;
        }
        if(sourceTop > height + bufferV) {
            blnAbove = true;
        }

        var top = 0;
        var left = 0;
        if(blnAbove) top = sourceTop - height - 20;
        else top = sourceTop + 10;
        if(blnLeft) left = sourceLeft - width - 4;
        else left = sourceLeft - 6;

        $ax.globals.MaxZIndex = $ax.globals.MaxZIndex + 1;
        if(IE_10_AND_BELOW) height += 50;

        var dObj = $ax.getObjectFromElementId(id);
        var ann = dObj.annotation;
        var $dialog = $('<div></div>')
            .appendTo('body')
            .html($ax.legacy.GetAnnotationHtml(ann))
            .dialog({
                title: dObj.label,
                width: width,
                height: height,
                minHeight: 150,
                zIndex: $ax.globals.MaxZIndex,
                position: [left, top],
                dialogClass: 'dialogFix',
                autoOpen: false
            });
        $dialog.parent().appendTo('#base');
        $dialog.dialog('open');
        dialogs[id] = $dialog;

        // scroll ... just for IE
        window.scrollTo(scrollX, scrollY);
    };

    $ax.annotation.InitializeAnnotations = function (query) {
        if(!$ax.document.configuration.showAnnotations) return;

        query.each(function(dObj, elementId) {
            if(!dObj.annotation) return;

            if(dObj.type == 'hyperlink') {
                var textId = $ax.style.GetTextIdFromLink(elementId);

                var elementIdQuery = $('#' + elementId);
                elementIdQuery.after("<span id='" + elementId + "_ann'>&#8203;</span>");

                if($ax.document.configuration.useLabels) {
                    var label = $('#' + elementId).attr("data-label");
                    if(!label || label == "") label = "?";
                    $('#' + textId).append("<div id='" + elementId + "Note' class='annnotelabel' >" + label + "</div>");
                } else {
                    $('#' + textId).append("<div id='" + elementId + "Note' class='annnoteimage' ></div>");
                }
                $('#' + elementId + 'Note').click(function(e) {
                    $ax.annotation.ToggleWorkflow(e, elementId, 300, 200, false);
                    return false;
                });

                _updateLinkLocations(textId);
            } else {
                if($ax.document.configuration.useLabels) {
                    var label = $('#' + elementId).attr("data-label");
                    if(!label || label == "") label = "?";
                    $('#' + elementId + "_ann").append("<div id='" + elementId + "Note' class='annnotelabel'>" + label + "</div>");
                } else {
                    $('#' + elementId + "_ann").append("<div id='" + elementId + "Note' class='annnoteimage'></div>");
                }
                $('#' + elementId + 'Note').click(function(e) {
                    $ax.annotation.ToggleWorkflow(e, elementId, 300, 200, false);
                    return false;
                });
            }

            $('#' + elementId + 'Note.annnoteimage').append("<div class='annnoteline'></div><div class='annnoteline'></div><div class='annnoteline'></div>");
        });
    };

    $ax.annotation.jQueryAnn = function(query) {
        var elementIds = [];
        query.each(function(diagramObject, elementId) {
            if(diagramObject.annotation) elementIds[elementIds.length] = elementId;
        });
        var elementIdSelectors = jQuery.map(elementIds, function(elementId) { return '#' + elementId + '_ann'; });
        var jQuerySelectorText = (elementIdSelectors.length > 0) ? elementIdSelectors.join(', ') : '';
        return $(jQuerySelectorText);
    };

    $(window.document).ready(function() {
        $ax.annotation.InitializeAnnotations($ax(function(dObj) { return dObj.annotation; }));

        $ax.messageCenter.addMessageListener(function(message, data) {
            //If the annotations are being hidden via the Sitemap toggle button, hide any open dialogs
            if(message == 'annotationToggle') {
                if(data == false) {
                    for(var index in dialogs) {
                        var $dialog = dialogs[index];
                        if($dialog.dialog("isOpen")) {
                            $dialog.dialog("close");
                        }
                    }
                }
            }
        });
    });

});