// ******* Object Model ******** //
$axure.internal(function($ax) {
    var _implementations = {};

    var _initializeObject = function(type, obj) {
        $.extend(obj, _implementations[type]);
    };
    $ax.initializeObject = _initializeObject;

    var _model = $ax.model = {};

    _model.idsInRdo = function(rdoId, scriptIds) {
        var rdoScriptId = $ax.repeater.getScriptIdFromElementId(rdoId);
        var path = $ax.getPathFromScriptId(rdoScriptId);
        var rdoRepeater = $ax.getParentRepeaterFromScriptId(rdoScriptId);

        if(!scriptIds) scriptIds = [];
        $ax('*').each(function(obj, elementId) {
            // Make sure in same rdo
            var scriptId = $ax.repeater.getScriptIdFromElementId(elementId);
            var elementPath = $ax.getPathFromScriptId(scriptId);
            // This is because last part of path is for the obj itself.
            elementPath.pop();
            if(elementPath.length != path.length) return;
            for(var i = 0; i < path.length; i++) if(elementPath[i] != path[i]) return;

            // If object is in a panel, the panel will be hidden, so the obj doesn't have to be.
            if(obj.parentDynamicPanel) return;

            var repeater = $ax.getParentRepeaterFromScriptId(scriptId);
            if(repeater != rdoRepeater) return;

            if($ax.public.fn.IsReferenceDiagramObject(obj.type)) _model.idsInRdo(scriptId, scriptIds);
            else if(scriptIds.indexOf(scriptId) != -1) return;
            // Kind of complicated, but returning for isContained objects, hyperlinks, tabel cell, non-root tree nodes, and images in the tree.
            else if (obj.isContained || obj.type == 'hyperlink' || $ax.public.fn.IsTableCell(obj.type) ||
                ($ax.public.fn.IsTreeNodeObject(obj.type) && !$jobj(elementId).hasClass('treeroot')) ||
                ($ax.public.fn.IsImageBox(obj.type) && $ax.public.fn.IsTreeNodeObject(obj.parent.type))) return;
            else scriptIds.push(scriptId);
        });
        return scriptIds;
    };

});