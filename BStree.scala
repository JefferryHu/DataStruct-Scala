

/**
 * @author Jeffery.Hu
 */
class BStree() {
  var left : BStree = null 
  var right : BStree = null
  var parent : BStree = null
  var data : java.lang.Integer = null
  
  def Insert(root: BStree, data : java.lang.Integer) {
    if (root == null) {
      val root = new BStree
      root.data = data
    }
    if(data < root.data) {
      if(root.left != null) 
        Insert(root.left, data)
      else { // insert left leaf
        var node = new BStree
        node.data = data
        node.parent = root
        root.left = node
      }
    } else {
      if (root.right != null)
        Insert(root.right, data)
      else { //insert right leaf
        var node = new BStree
        node.data = data
        node.parent = root
        root.right = node
      }
    }
  }
  
  def Search(root : BStree, data : java.lang.Integer) : BStree = {
    if (root == null) {
      root
    } else {
      if(root.data > data) { // left
        //println("Searching " + root.data + " " + data)
        return Search(root.left, data)
      } else if (root.data < data) { //right
        //println("Searching " + root.data + " " + data)
        return Search(root.right, data)
      } else { // found
        //println("Found!" + root.data)
        root
      }
    }
  }
  
  def Create(array : Array[java.lang.Integer]) {
    this.data = array(0)
    for( i <- 1 until array.length) {
      Insert(this, array(i))
    }
  }
  
  def PPrint(root : BStree) {
    if(root.left != null)
      PPrint(root.left)
    print(" " + root.data)
    if(root.right != null)
      PPrint(root.right)
  }
  
  def MaxNode(root : BStree) : BStree = {
    if(root == null) {
      return root
    }
    var tmp = root
    while(tmp.right != null) {
       tmp = tmp.right
    }
    tmp
  }
  
  def MinNode(root : BStree) : BStree = {
    if(root == null) {
      return root
    }
    var tmp = root
    while(tmp.left != null) {
      tmp = tmp.left
    }
    tmp
  }
  
  def Delete(data : java.lang.Integer) : BStree = {
    //printf("Search %d \n", data)
    var dst = this.Search(this, data)
    if (dst == null) {
      return dst
    }
    //printf("Delete %d \n", dst.data)
    if (dst.left == null && dst.right == null) {
      //无子树，直接删除
      if (dst.parent != null) {
        if(dst.parent.left != null && dst.data == dst.parent.left.data) {
          dst.parent.left = null
        } else {
          dst.parent.right = null       
        }
      }
    }
    else if (dst.left != null && dst.right == null) {
      //左子树不为空，右子树为空
      //找到左树最大
      var tmp = MaxNode(dst.left)
      tmp.parent.right = null
      dst.left.parent = tmp
      tmp.parent = dst.parent
      if (dst.parent != null) {
        if(dst.parent.left != null && dst.data == dst.left.data) {
          dst.parent.left = tmp
        } else {
          dst.parent.right = tmp
        }
      }
    } else {
      //右子树一定不为空
      //用右边最小替换
      var tmp = MinNode(dst.right)
      tmp.parent.left = null
      dst.right.parent = tmp
      if(dst.left != null) {
        dst.left.parent = tmp
      }
      if (dst.parent != null) {
        tmp.parent = dst.parent
        if(dst.parent.left != null && dst.data == dst.parent.left.data) {
          dst.parent.left = tmp
        } else {
          dst.parent.right = tmp
        }
      }
    }
    dst
  }
}
