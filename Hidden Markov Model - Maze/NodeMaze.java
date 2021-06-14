import org.w3c.dom.*;


   // import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.UserDataHandler;

    public class NodeMaze implements Node {
        private int x;
        private int y;


        NodeMaze(int x, int y){
            this.x = x;
            this.y = y;
        }


        public int getX(){
            return this.x;
        }

        public int getY(){
            return this.y;
        }

        @Override
        public int hashCode(){
            return this.getX()+this.getY()+31;
        }

        @Override
        public boolean equals(Object obj){
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            NodeMaze tmp = (NodeMaze) obj;
            return tmp.getX() == this.getX() && this.getY() == tmp.getY();
        }

        @Override
        public String toString(){
            return "x: " + this.getX() + " y: " + this.getY();
        }


        @Override
        public String getNodeName() {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public String getNodeValue() throws DOMException {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public void setNodeValue(String nodeValue) throws DOMException {
            // TODO Auto-generated method stub

        }


        @Override
        public short getNodeType() {
            // TODO Auto-generated method stub
            return 0;
        }


        @Override
        public Node getParentNode() {
            // TODO Auto-generated method stub
            return null;
        }


        public NodeList getChildNodes1() {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public Node getFirstChild() {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public Node getLastChild() {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public Node getPreviousSibling() {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public Node getNextSibling() {
            // TODO Auto-generated method stub
            return null;
        }


        public NamedNodeMap getAttributes1() {
            // TODO Auto-generated method stub
            return null;
        }


        public Document getOwnerDocument1() {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public Node insertBefore(Node newChild, Node refChild) throws DOMException {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public Node replaceChild(Node newChild, Node oldChild) throws DOMException {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public Node removeChild(Node oldChild) throws DOMException {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public Node appendChild(Node newChild) throws DOMException {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public boolean hasChildNodes() {
            // TODO Auto-generated method stub
            return false;
        }


        @Override
        public Node cloneNode(boolean deep) {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public void normalize() {
            // TODO Auto-generated method stub

        }


        @Override
        public boolean isSupported(String feature, String version) {
            // TODO Auto-generated method stub
            return false;
        }


        @Override
        public String getNamespaceURI() {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public String getPrefix() {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public void setPrefix(String prefix) throws DOMException {
            // TODO Auto-generated method stub

        }


        @Override
        public String getLocalName() {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public boolean hasAttributes() {
            // TODO Auto-generated method stub
            return false;
        }


        @Override
        public String getBaseURI() {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public short compareDocumentPosition(Node other) throws DOMException {
            // TODO Auto-generated method stub
            return 0;
        }


        @Override
        public String getTextContent() throws DOMException {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public void setTextContent(String textContent) throws DOMException {
            // TODO Auto-generated method stub

        }


        @Override
        public boolean isSameNode(Node other) {
            // TODO Auto-generated method stub
            return false;
        }


        @Override
        public String lookupPrefix(String namespaceURI) {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public boolean isDefaultNamespace(String namespaceURI) {
            // TODO Auto-generated method stub
            return false;
        }


        @Override
        public String lookupNamespaceURI(String prefix) {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public boolean isEqualNode(Node arg) {
            // TODO Auto-generated method stub
            return false;
        }


        @Override
        public Object getFeature(String feature, String version) {
            // TODO Auto-generated method stub
            return null;
        }


        public Object setUserData1(String key, Object data, UserDataHandler handler) {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public Object getUserData(String key) {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public NodeList getChildNodes() {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public NamedNodeMap getAttributes() {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public Document getOwnerDocument() {
            // TODO Auto-generated method stub
            return null;
        }


        @Override
        public Object setUserData(String key, Object data, UserDataHandler handler) {
            // TODO Auto-generated method stub
            return null;
        }

}
