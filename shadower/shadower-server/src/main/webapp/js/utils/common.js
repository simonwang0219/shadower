/***
 * 判断对象是否为空
 * @param obj
 * @returns
 */
function isEmpty(obj) {
	if (obj === null || obj === undefined || obj === '') {
		return true;
	}
	return false;
}

/**
 * 比较器（升序）
 * @param key
 * @returns
 */
function compare(key) {
	return function(object1, object2) {
		var value1 = object1[key];
		var value2 = object2[key];
		if (value1 < value2) {
			return -1;
		} else if (value1 > value2) {
			return 1;
		} else {
			return 0;
		}
	};
}

/**
 * 将int转换为字符串ip
 * 
 * @param ipInt
 * @returns
 */
function intToIp(ipInt) {
	var ipStr = '';
	ipStr += (ipInt >> 24) & 0xff;
	ipStr += '.';
	ipStr += (ipInt >> 16) & 0xff;
	ipStr += '.'
	ipStr += (ipInt >> 8) & 0xff;
	ipStr += '.';
	ipStr += ipInt & 0xff;
	return ipStr;
}

//获取鼠标坐标  
function mousePosition(ev){   
    ev = ev || window.event;   
    if(ev.pageX || ev.pageY){   
        return {x:ev.pageX, y:ev.pageY};   
    }   
    return {   
        x:ev.clientX + document.body.scrollLeft - document.body.clientLeft,   
        y:ev.clientY + document.body.scrollTop - document.body.clientTop   
    };   
}
