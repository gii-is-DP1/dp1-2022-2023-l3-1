
<%@ attribute name="ocaBoard" required="false" rtexprvalue="true" type="org.springframework.samples.parchisoca.board.OcaBoard" description="Oca Board to be rendered" %>

<canvas id="canvas" width="${ocaBoard.width}" height="${ocaBoard.height}"></canvas>
<img id="source" src="${ocaBoard.background}" style="display:none">
<img id="RED" src="/resources/images/RED.png" style="display:none">
<img id="BLUE" src="/resources/images/BLUE.png" style="display:none">
<img id="YELLOW" src="/resources/images/YELLOW.png" style="display:none">
<img id="GREEN" src="/resources/images/GREEN.png" style="display:none">
<script>
function drawBoard(){ 
    var canvas = document.getElementById("canvas");
    var ctx = canvas.getContext("2d");
    var image = document.getElementById('source');
    ctx.drawImage(image, 0, 0, ${ocaBoard.width}, ${ocaBoard.height});
    <jsp:doBody/>
}
window.onload = drawBoard();
</script>
