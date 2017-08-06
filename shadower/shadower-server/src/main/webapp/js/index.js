var displayJson = "";
var parentIdToChildsMap = null;
var idToSpanMap = null;
$(function() {
	$("#queryBtn").click(function() {
		var traceId = $("#traceIdTxt").val();
		$.getJSON('tracker/' + traceId, function(data) {
			if (data.code == 200) {
				var spans = data.result;
				if (spans.length > 0) {
					$.each(spans, function(key, obj) {
						convertSpan(obj);
					});
					spans.sort(compare("id"));
					displayJson = formatJson(JSON.stringify(spans));
					parentIdToChildsMap = getParentIdToChildsMap(spans);
					traversalMap(parentIdToChildsMap);
					idToSpanMap = getIdToSpanMap(spans);
					bindClickToSpans();

				}
			}
		});
	});

});


function bindClickToSpans() {
	$(".span").click(function() {
		var id = $(this).attr("id");
		var span = idToSpanMap[id];
		var content = formatJson(JSON.stringify(span));
		showModal(content);
	});

}

function showModal(content) {
	$("#jsonModal .modal-body").html("<pre>" + content + "</pre>");
	$("#jsonModal").modal("show");
}


$("#jsonBtn").click(function() {
	showModal(displayJson);
});