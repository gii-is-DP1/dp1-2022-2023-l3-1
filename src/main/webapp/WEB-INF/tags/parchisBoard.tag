<%@ attribute name="parchisBoard" required="false" rtexprvalue="true" type="org.springframework.samples.petclinic.board.ParchisBoard"
 description="ParchisBoard to be rendered" %>
<canvas id="canvas" width="${parchisBoard.width}" height="${parchisBoard.height}"></canvas>
<img id="source" src="${parchisBoard.background}" style="display:none">
<img id="RED" src="resources/images/RED.png" style="display:none">
<img id="BLUE" src="resources/images/BLUE.png" style="display:none">
<img id="GREEN" src="resources/images/GREEN.png" style="display:none">
<img id="YELLOW" src="resources/images/YELLOW.png" style="display:none">
<script>
function drawBoard(){ 
    var canvas = document.getElementById("canvas");
    var ctx = canvas.getContext("2d");
    var image = document.getElementById('source');
    ctx.drawImage(image, 0, 0, ${parchisBoard.width}, ${parchisBoard.height});     
    <jsp:doBody/>
}
window.onload =drawBoard();
</script>