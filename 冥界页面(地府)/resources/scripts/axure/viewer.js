// ******* SITEMAP TOOLBAR VIEWER ACTIONS ******** //
$axure.internal(function ($ax) {
    var userTriggeredEventNames = ['onClick', 'onDoubleClick', 'onMouseOver', 'onMouseMove', 'onMouseOut', 'onMouseDown', 'onMouseUp',
        'onKeyDown', 'onKeyUp', 'onFocus', 'onLostFocus', 'onTextChange', 'onSelectionChange', 'onSelectedChange', 'onSelect', 'onUnselect',
        'onSwipeLeft', 'onSwipeRight', 'onSwipeUp', 'onSwipeDown', 'onDragStart', 'onDrag', 'onDragDrop', 'onScroll', 'onContextMenu', 'onMouseHover', 'onLongClick'];

    $ax.messageCenter.addMessageListener(function(message, data) {
        //If annotation toggle message received from sitemap, toggle footnotes
        if(message == 'annotationToggle') {
            if(data == true) {
                $('div.annotation').show();
                $('div.annnotelabel').show();
                $('div.annnoteimage').show();
            } else {
                $('div.annotation').hide();
                $('div.annnotelabel').hide();
                $('div.annnoteimage').hide();
            }
        }
    });

    var lastSelectedWidgetNote;
    $ax.messageCenter.addMessageListener(function (message, data) {
        //If annotation toggle message received from sitemap, toggle footnotes
        if(message == 'toggleSelectWidgetNote') {
            if(lastSelectedWidgetNote == data) {
                $('#' + lastSelectedWidgetNote).removeClass('widgetNoteSelected');
                lastSelectedWidgetNote = null;
                return;
            }

            if(lastSelectedWidgetNote) $('#' + lastSelectedWidgetNote).removeClass('widgetNoteSelected');
            $('#' + data).addClass('widgetNoteSelected');
            lastSelectedWidgetNote = data;
        }
    });

    var highlightEnabled = false;
    $ax.messageCenter.addMessageListener(function(message, data) {
        if(message == 'highlightInteractive') {
            highlightEnabled = data == true;
            _applyHighlight($ax('*'));
        }
    });

    var _applyHighlight = $ax.applyHighlight = function(query, ignoreUnset) {
        if(ignoreUnset && !highlightEnabled) return;

        //Do condition to check if legacy browser (all IE, except 10) and select appropriate pulsate css class name
        var userAgentString = navigator.userAgent.toLowerCase();

        var isIEpre10 = userAgentString.indexOf('msie 9.') != -1 ||
                userAgentString.indexOf('msie 8.') != -1 ||
                userAgentString.indexOf('msie 7.') != -1 ||
                userAgentString.indexOf('msie 6.') != -1;

        var pulsateClassName = 'legacyPulsateBorder';

        //Find all widgets with a defined userTriggeredEventName specified in the array above
        var $matchingElements = query.filter(function(obj) {
            if(obj.interactionMap) {
                for(var index in userTriggeredEventNames) {
                    if(obj.interactionMap[userTriggeredEventNames[index]]) return true;
                }
            } else if ($ax.public.fn.IsVector(obj.type) && obj.referencePageUrl) {
                return true;
            }
            return false;
        }).$();

        var isHighlighted = $matchingElements.is('.' + pulsateClassName);

        //Toggle the pulsate class on the matched elements
        if(highlightEnabled && !isHighlighted) {
            $matchingElements.addClass(pulsateClassName);
        } else if(!highlightEnabled && isHighlighted) {
            $matchingElements.removeClass(pulsateClassName);
        }
    };
});