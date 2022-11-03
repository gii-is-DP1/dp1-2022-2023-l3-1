<%@ attribute name="size" required="true" rtexprvalue="true" 
 description="Size of the field to show" %>
 <%@ attribute name="field" required="true" rtexprvalue="true" type="org.springframework.samples.petclinic.tablero.TableroOca"
 description="Field to be rendered" %>
<img id="source" src="${TableroOca.background}" style="display:none">
<img id="PIEZA-1" src="src/main/resources/static/Resources/Images/png-transparent-chess-piece-ludo-game-plum-purple-game-violet-thumbnail.png" style="display:none">
<script>
function drawBoard(){ 
    var canvas = document.getElementById("canvas");
    var ctx = canvas.getContext("2d");
    var image = document.getElementById('source');
    ctx.drawImage(image, 0, 0, ${TableroOca.width}, ${TableroOca.height});     
    <jsp:doBody/>
}
window.onload =drawBoard();
</script>
