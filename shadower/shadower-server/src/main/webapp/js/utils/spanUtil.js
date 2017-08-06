/**
 * 数据格式转换
 */
function convertSpan(span) {
	convertAnnotation(span.cs);
	convertAnnotation(span.cr);
	convertAnnotation(span.ss);
	convertAnnotation(span.sr);
}

function convertAnnotation(annotation) {
	var endPoint = annotation.endPoint;
	if (!isEmpty(endPoint) && !isEmpty(endPoint.ipV4)) {
		endPoint.ipV4 = intToIp(endPoint.ipV4);
	}
}


/***
 *  key:parentId, value:childSpans
 */
function getParentIdToChildsMap(spans) {
	var map = new Object();
	for (var i = 0; i < spans.length; i++) {
		var id = spans[i].id;
		var childSpans = [];
		for (var j = 0; j < spans.length; j++) {
			if (i == j) {
				continue;
			}
			var span = spans[j];
			if (!isEmpty(span.parentId)) {
				if (id == span.parentId) {
					childSpans.push(span);
				}
			} else {
				// 根span
				if (isEmpty(map[id])) {
					map[-1] = new Array(span);
				}
			}
		}
		//childSpans.sort(compare("id"));
		map[id] = childSpans;
	}
	return map;
}

/***
 *  key:id, value:span
 */
function getIdToSpanMap(spans) {
	var map = new Object();
	for (var i = 0; i < spans.length; i++) {
		map[spans[i].id]=spans[i];
	}
	return map;
}

function traversalMap(map) {
	if (isEmpty(map)) {
		return;
	}
	var rootSpan = map[-1][0];
	var childSpans = map[rootSpan.id];

	//测试数据
	/*
	rootSpan=new Object();
	childSpans=[];
	generateTestData(rootSpan, childSpans);
	*/
	
	var html = "";
	var right = 0;
	for (var i = childSpans.length - 1; i >= 0; i--) {
		right += childSpans[i].duration;
		left = rootSpan.duration - right
		html = getDisplay(childSpans[i], rootSpan, left) + html;
	}
	html = getRootDisplay(rootSpan) + html;
	$("#container").html(html);
}


function getRootDisplay(span) {
	return "<div class='span root'"+" id="+span.id+">" + span.name + "," + span.duration + "ms"+" ("+span.duration/1000+"s)"
			+ "</div>";
}

function getDisplay(span, parentSpan, left) {
	var widthScale = (span.duration / parentSpan.duration) * 100 + "%";
	var leftScale = (left / parentSpan.duration) * 100 + "%";

	return "<div class='span'"+" id="+span.id+"><div class='child' style='width:" + widthScale
			+ ";left:" + leftScale + "'>" + span.name + " , " + span.duration
			+ "ms" +" ("+span.duration/1000+"s)"+ "</div></div>"
}


function generateTestData(rootSpan, childSpans) {
	
	var total = 100;
	var current = 0;
	
	rootSpan.name = "root";
	rootSpan.duration = total;

	var i = 0;
	for (; i < 5; i++) {
		var span = new Object();
		span.name = "test-" + i;
		var duration = 10 * (i + 1);
		if (current + duration > total) {
			break;
		}
		current += duration;
		span.duration = duration;
		childSpans.push(span);
	}
	if (current < total) {
		var span = new Object();
		span.name = "test-" + i;
		span.duration = total - current;
		childSpans.push(span);
	}
}
