<%@ attribute name="size" required="true" rtexprvalue="true" 
 description="Size of the piece to show" %>
 <%@ attribute name="piece" required="true" rtexprvalue="true" type="org.springframework.samples.parchisoca.piece.OcaPiece"
 description="Piece to be rendered" %>
 
 image = document.getElementById('${piece.colour}');
 ctx.drawImage(image,${piece.getXPosition()},${piece.getYPosition()},30,30);
 <%-- ctx.drawImage(image,180,405,30,30); --%>
