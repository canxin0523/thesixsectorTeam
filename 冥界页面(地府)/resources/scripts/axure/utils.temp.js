// ******* Deep Copy ******** //
$axure.internal(function($ax) {
    // TODO: [ben] Ah, infinite loops cause major issues here. Tried saving objects we've already hit, but that didn't seem to work (at least at my first shot).
    var _deepCopy = function(original, trackCopies) {
        if(trackCopies) {
            var index = _getCopyIndex(original);
            if(index != -1) return _originalToCopy[index][1];
        }
        var isArray = original instanceof Array;
        var isObject = !(original instanceof Function) && !(original instanceof Date) && (original instanceof Object);
        if(!isArray && !isObject) return original;
        var copy = isArray ? [] : { };
        if(trackCopies) _originalToCopy.push([original, copy]);
        isArray ? deepCopyArray(original, trackCopies, copy) : deepCopyObject(original, trackCopies, copy);
        return copy;
    };
    $ax.deepCopy = _deepCopy;

    // Hacky way to copy event info. Copying dragInfo causes major issues due to infinite loops
    // Hashmap doesn't map objects well. It just toStrings them, making them all the same key. This has to be slow...
    var _originalToCopy = [];
    var _getCopyIndex = function(original) {
        for(var i = 0; i < _originalToCopy.length; i++) if(original === _originalToCopy[i][0]) return i;
        return -1;
    };

    $ax.eventCopy = function(eventInfo) {
        var dragInfo = eventInfo.dragInfo;
        delete eventInfo.dragInfo;
        var copy = _deepCopy(eventInfo, true);
        copy.dragInfo = dragInfo;
        eventInfo.dragInfo = dragInfo;
        // reset the map.
        _originalToCopy = [];

        return copy;
    };

    var deepCopyArray = function(original, trackCopies, copy) {
        for(var i = 0; i < original.length; i++) {
            copy[i] = _deepCopy(original[i], trackCopies);
        }
    };

    var deepCopyObject = function(original, trackCopies, copy) {
        for(var key in original) {
            if(!original.hasOwnProperty(key)) continue;
            copy[key] = _deepCopy(original[key], trackCopies);
        }
    };

    // Our implementation of splice because it is broken in IE8...
    $ax.splice = function(array, startIndex, count) {
        var retval = [];
        if(startIndex >= array.length || startIndex < 0 || count == 0) return retval;
        if(!count || startIndex + count > array.length) count = array.length - startIndex;
        for(var i = 0; i < count; i++) retval[i] = array[startIndex + i];
        for(i = startIndex + count; i < array.length; i++) array[i - count] = array[i];
        for(i = 0; i < count; i++) array.pop();
        return retval;
    };
});



// ******* Flow Shape Links ******** //
$axure.internal(function($ax) {

    if(!$ax.document.configuration.linkFlowsToPages && !$ax.document.configuration.linkFlowsToPagesNewWindow) return;

    $(window.document).ready(function() {
        $ax(function (dObj) { return ($ax.public.fn.IsVector(dObj.type) || $ax.public.fn.IsSnapshot(dObj.type)) && dObj.referencePageUrl; }).each(function (dObj, elementId) {

            var elementIdQuery = $('#' + elementId);

            if($ax.document.configuration.linkFlowsToPages && !$ax.event.HasClick(dObj)) {
                elementIdQuery.css("cursor", "pointer");
                elementIdQuery.click(function() {
                    $ax.navigate({
                        url: dObj.referencePageUrl,
                        target: "current",
                        includeVariables: true
                    });
                });
            }

            if($ax.document.configuration.linkFlowsToPagesNewWindow) {
                $('#' + elementId + "_ref").append("<div id='" + elementId + "PagePopup' class='refpageimage'></div>");
                $('#' + elementId + "PagePopup").click(function() {
                    $ax.navigate({
                        url: dObj.referencePageUrl,
                        target: "new",
                        includeVariables: true
                    });
                });
            }
        });
    });

});
