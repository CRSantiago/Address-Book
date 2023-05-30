/* Christopher Santiago
 * Dr. Andrew Steinberg
 * COP 3503 Summer 2022
 * Programming Assignment 2
 */

class Node<T extends Comparable<T>, U>{
  public T name;
  public U office;
  public int color;
  public Node<T,U> parent;
  public Node<T,U> leftChild;
  public Node<T,U> rightChild;
}

public class AddressBookTree<T extends Comparable<T>, U> {
  public Node<T,U> root;
  public Node<T,U> Tnil = new Node<T,U>();

  public AddressBookTree(){
    Tnil.color = 0;
    Tnil.leftChild = null;
		Tnil.rightChild = null;
    this.root = Tnil;
  }

  public void display(){
    displayHelper(this.root);
  }

  public void displayHelper(Node<T,U> node) {
    if (node != Tnil) {
      displayHelper(node.leftChild);
      System.out.println(node.name + " " + node.office);
      displayHelper(node.rightChild);
    }
  }

  public void insert(T name, U office){
    Node<T,U> nodeZ = new Node<T,U>();
    nodeZ.name = name;
    nodeZ.office = office;
    nodeZ.leftChild = Tnil;
    nodeZ.rightChild = Tnil;
    nodeZ.color = 1;

    Node<T,U> y = Tnil;
    Node<T,U> x = this.root;

    while(x != Tnil){
      y = x;
      if(nodeZ.name.compareTo(x.name) < 0){
        x = x.leftChild;
      } else {
        x = x.rightChild;
      }
    }

    nodeZ.parent = y;
    if(y == Tnil){
      root = nodeZ;
    } else if(nodeZ.name.compareTo(y.name) < 0){
      y.leftChild = nodeZ;
    } else {
      y.rightChild = nodeZ;
    }
    insertFix(nodeZ);
  } //end of insert

  public void insertFix(Node<T,U> nodeZ){
    Node<T, U> y; 
    while(nodeZ.parent.color == 1){
      if(nodeZ.parent == nodeZ.parent.parent.leftChild){
        y = nodeZ.parent.parent.rightChild;
        //case 1
        if(y.color == 1){
          nodeZ.parent.color = 0;
          y.color = 0;
          nodeZ.parent.parent.color = 1;
          nodeZ = nodeZ.parent.parent;
          //end of case 1
        } else {
          // case 2
          if(nodeZ == nodeZ.parent.rightChild){
            nodeZ = nodeZ.parent;
            leftRotate(nodeZ);
          } //end of case 2
          //case 3
          nodeZ.parent.color = 0;
          nodeZ.parent.parent.color = 1;
          rightRotate(nodeZ.parent.parent);
        }//end of case 3
      } else {
        // same as above but right left exchange
        y = nodeZ.parent.parent.leftChild;
        if(y.color == 1){
          nodeZ.parent.color = 0;
          y.color = 0;
          nodeZ.parent.parent.color = 1;
          nodeZ = nodeZ.parent.parent;
        } else {
          if(nodeZ == nodeZ.parent.leftChild){
            nodeZ = nodeZ.parent;
            rightRotate(nodeZ);
          }
          nodeZ.parent.color = 0;
          nodeZ.parent.parent.color = 1;
          leftRotate(nodeZ.parent.parent);
        }
      }
    }
    root.color = 0;
  } //end of insertFix
  
  public void leftRotate(Node<T,U> nodeX){
    Node<T,U> y = nodeX.rightChild; //set y
    nodeX.rightChild = y.leftChild; // turn y's subtree into x's right subtree
    if(y.leftChild != Tnil){
      y.leftChild.parent = nodeX;
    }
    y.parent = nodeX.parent; // link x's parent to y 
    if(nodeX.parent == Tnil){
      root = y;
    } else if (nodeX == nodeX.parent.leftChild){
      nodeX.parent.leftChild = y;
    } else {
      nodeX.parent.rightChild = y;
    }
    y.leftChild = nodeX; // put x on y's left
    nodeX.parent = y;
  }//end of leftRotate

  public void rightRotate(Node<T,U> nodeX){
    Node<T,U> y = nodeX.leftChild;
    nodeX.leftChild = y.rightChild;
    if(y.rightChild != Tnil){
      y.rightChild.parent = nodeX;
    }
    y.parent = nodeX.parent;
    if(nodeX.parent == Tnil){
      root = y;
    } else if (nodeX == nodeX.parent.rightChild){
      nodeX.parent.rightChild = y;
    } else {
      nodeX.parent.leftChild = y;
    }
    y.rightChild = nodeX;
    nodeX.parent = y;
  } //end of rightRotate

  public void transplant(Node<T,U> nodeU, Node<T,U> nodeV) {
    if(nodeU.parent == null) {
      root = nodeV;
    } else if (nodeU == nodeU.parent.leftChild){
      nodeU.parent.leftChild = nodeV;
    } else {
      nodeU.parent.rightChild = nodeV;
    }
    nodeV.parent = nodeU.parent;
  } // end of transplant

  public void deleteNode(T name){
    deleteHelper(this.root, name);
  }

  public void deleteHelper(Node<T,U> node, T name) {
    Node<T,U> nodeZ = Tnil;
    Node<T,U> nodeX, nodeY;
    
    while(node != Tnil){
      if(node.name.compareTo(name) == 0){
        nodeZ = node;
      }

      if(node.name.compareTo(name) <= 0){
        node = node.rightChild;
      } else {
        node = node.leftChild;
      }
    }
    nodeY = nodeZ;
    int yOriginalColor = nodeY.color;
    if(nodeZ.leftChild == Tnil){
      nodeX = nodeZ.rightChild;
      transplant(nodeZ, nodeZ.rightChild);
    } else if(nodeZ.rightChild == Tnil){
      nodeX = nodeZ.leftChild;
      transplant(nodeZ, nodeZ.leftChild);
    } else {
      nodeY = minimum(nodeZ.rightChild);
      yOriginalColor = nodeY.color;
      nodeX = nodeY.rightChild;
      
      if(nodeY.parent == nodeZ){
        nodeX.parent = nodeY;
      } else {
        transplant(nodeY, nodeY.rightChild);
        nodeY.rightChild = nodeZ.rightChild;
        nodeY.rightChild.parent = nodeY;
      }
      transplant(nodeZ, nodeY);
      nodeY.leftChild = nodeZ.leftChild;
      nodeY.leftChild.parent = nodeY;
      nodeY.color = nodeZ.color;
    }
    if(yOriginalColor == 0) {
      deleteFix(nodeX);
    }
  }//end of deleteHelper

  public Node<T,U> minimum(Node<T,U> node) {
    while (node.leftChild != Tnil) {
      node = node.leftChild;
    }
    return node;
  }

  public void deleteFix(Node<T,U> nodeX) {
    Node<T,U> nodeW;
    while(nodeX != root && nodeX.color == 0){
      if(nodeX == nodeX.parent.leftChild){
        nodeW = nodeX.parent.rightChild;
        //case 1
        if(nodeW.color == 1){
          nodeW.color = 0;
          nodeX.parent.color = 1;
          leftRotate(nodeX.parent);
          nodeW = nodeX.parent.rightChild;
        } //end of case 1

        //case 2
        if(nodeW.leftChild.color == 0 && nodeW.rightChild.color == 0) {
          nodeW.color = 1;
          nodeX = nodeX.parent;
          //end of case 2
        } else {
          //case 3
          if(nodeW.rightChild.color == 0) {
            nodeW.leftChild.color = 0;
            nodeW.color = 1;
            rightRotate(nodeW);
            nodeW = nodeX.parent.rightChild;
          }// end of case 3
          
          //case 4
          nodeW.color = nodeX.parent.color;
          nodeX.parent.color = 0;
          nodeW.rightChild.color = 0;
          leftRotate(nodeX.parent);
          nodeX = root;
        } //end of case 4
      } else {
        // same as above with right and left exchange
        nodeW = nodeX.parent.leftChild;
        if(nodeW.color == 1){
          nodeW.color = 0;
          nodeX.parent.color = 1;
          rightRotate(nodeX.parent);
          nodeW = nodeX.parent.leftChild;
        }

        if(nodeW.leftChild.color == 0  && nodeW.rightChild.color == 0){
          nodeW.color = 1;
          nodeX = nodeX.parent;
        } else {
          if(nodeW.leftChild.color == 0){
            nodeW.rightChild.color = 0;
            nodeW.color = 1;
            leftRotate(nodeW);
            nodeW = nodeX.parent.leftChild;
          }
          nodeW.color = nodeX.parent.color;
          nodeX.parent.color = 0;
          nodeW.leftChild.color = 0;
          rightRotate(nodeX.parent);
          nodeX = root;
        }
      }
    }
    nodeX.color = 0;
  } // end of deleteFix

  public Node<T,U> getRoot() {
    return this.root;
  }

  public int countRed(Node<T,U> node) {
    int redCount = 0;
    if(node == Tnil){
      return 0;
    }
    redCount += countRed(node.leftChild);
    redCount += countRed(node.rightChild);

    if(node.color == 1) {
      redCount++;
    }
    return redCount;
  }//end of countRed

  public int countBlack(Node<T,U> node) {
    int blackCount = 0;
    if(node == Tnil){
      return 0;
    }
    blackCount += countBlack(node.leftChild);
    blackCount += countBlack(node.rightChild);

    if(node.color == 0) {
      blackCount++;
    }
    return blackCount;
  }//end of countBlack
}
