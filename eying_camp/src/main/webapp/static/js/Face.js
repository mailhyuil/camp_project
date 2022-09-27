var Dot = function (x, y) {
    this.x = x;
    this.y = y;
    this.add = function (w) { this.x += w.x; this.y += w.y; return this; };
    this.min = function (w) { this.x -= w.x; this.y -= w.y; return this; };
};

Math.radians = function (degrees) { return degrees * Math.PI / 180; };
Math.degrees = function (radians) { return radians * 180 / Math.PI; };
//get degrees between two dots
Math.get_deg_between = function (a, b) { var deg = (Math.degrees(Math.atan2(a.x - b.x, a.y - b.y)) * (-1) - 90); return (deg < 0) ? 360 + deg : deg; };

function getRelativeDot(m, deg, dist) {
    return new Dot(
        m.x + Math.cos(Math.radians(deg)) * dist
        , m.y + Math.sin(Math.radians(deg)) * dist
    );
}

document.addEventListener("mousemove", myFunction);
function myFunction(e) {
    const mouse = new Dot(e.clientX, e.clientY);
    const eyes = document.querySelectorAll(".eye");
    for (let i = 0; i < eyes.length; i++) {
        const eye = eyes[i];
        const eye_position = getPosition(eye);
        const eye_mid = new Dot(eye.offsetWidth / 2, eye.offsetWidth / 2);
        const pill = eye.querySelectorAll('.pill')[0];
        const degrees = Math.get_deg_between(eye_position, mouse);
        const distance = (eye.offsetWidth / 2 - pill.offsetWidth / 2);
        const new_pill = getRelativeDot(eye_mid, degrees, distance);
        const minus = new Dot(pill.offsetWidth / 2, pill.offsetWidth / 2);
        new_pill.min(minus);
        pill.style.top = new_pill.y + 'px';
        pill.style.left = new_pill.x + 'px';

    }
}

function getPosition(el) {
    let x = 0;
    let y = 0;
    while (el && !isNaN(el.offsetLeft) && !isNaN(el.offsetTop)) {
        x += el.offsetLeft - el.scrollLeft;
        y += el.offsetTop - el.scrollTop;
        el = el.offsetParent;
    }
    return new Dot(x, y);
}