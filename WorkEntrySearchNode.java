package main;
/**
 * Explanation: Implementation of WorkEntrySearchNode
 * Known Bugs:None
 * Zheng Chu
 * zhengchu@brandeis.edu
 * Nov 7, 2020
 * COSI 21A PA2
 */
public class WorkEntrySearchNode implements Comparable<WorkEntrySearchNode> {
	
	public WorkEntrySearchNode left; // KEEP THIS PUBLIC 

	public WorkEntrySearchNode right; // KEEP THIS PUBLIC 

	public WorkEntrySearchNode parent; // KEEP THIS PUBLIC
	public WorkEntry[] data;
	public String activity;
	/**
	 * constructor of WorkEntrySearchNode
	 * @param activity key
	 */
	public WorkEntrySearchNode(String activity) {
		this.activity=activity;
		data=null;
		left=null;
		right=null;
		parent=null;
	}
	/**
	 * compare two nodes based on activity key
	 * @param A node named other
	 */
	public int compareTo(WorkEntrySearchNode other) {
		return this.activity.compareTo(other.activity);
	}
	/**
	 * search for node in splay tree, if not found, return the last traversed node
	 * @param A node named node
	 * @return the searching result
	 */
	public WorkEntrySearchNode search(WorkEntrySearchNode node) {
		if(this==null||this.compareTo(node)==0) {
			return this;
		}
		WorkEntrySearchNode res=null;
		WorkEntrySearchNode current=this;
		while(current!=null) {
			if(current.compareTo(node)==0) {
				current.splay();
				return current;
			}
			//node is in left subtree
			else if(current.compareTo(node)>0) {
				res=current;
				current=current.left;
			}
			//node is in right subtree
			else {
				res=current;
				current=current.right;
			}
		}
		res.splay();
		return res;
	}
	/**
	 * insert a node in splay tree
	 * @param A node named node
	 * @return The node matches the node or the node after being splayed
	 */
	public WorkEntrySearchNode insert(WorkEntrySearchNode node) {
		if(this==null||this.compareTo(node)==0) {
			return this;
		}
		WorkEntrySearchNode res=null;
		WorkEntrySearchNode current=this;
		while(current!=null) {
			if(current.compareTo(node)==0) {
				current.splay();
				return current;
			}
			//node is in left subtree
			else if(current.compareTo(node)>0) {
				res=current;
				current=current.left;
			}
			//node is in right subtree
			else {
				res=current;
				current=current.right;
			}
		}
		node.parent=res;
		if(res!=null) {
			if(res.compareTo(node)>0) {
				res.left=node;
			}
			else {
			    res.right=node;	
			}
		}
		node.splay();
		return node;
	}
	/**
	 * return an inorder traversal of tree
	 * @return The string representation
	 */
	public String toString() {
		StringBuilder res=new StringBuilder();
		WorkEntrySearchNode root=this;
		toString_helper(root,res);
		return res.toString();
	}
	/**
	 * return an inorder traversal of tree structure with parenthesis
	 * @return The string representation
	 */
	public String getStructure() {
		StringBuilder res=new StringBuilder();
		WorkEntrySearchNode root=this;
		structure_helper(root,res);
		return res.toString();
	}
	/**
	 * add a new WorkEntry if it matches this one
	 */
	public void add(WorkEntry e) {
		if(e.getActivity().equals(this.activity)) {
			if(data==null) {
				data=new WorkEntry[1];
				data[0]=e;
			}
			else {
			WorkEntry[] temp=new WorkEntry[data.length+1];
			for(int i=0;i<temp.length-1;i++) {
				temp[i]=data[i];
			}
			temp[temp.length-1]=e;
			data=temp;
			}
		}
	}
	/**
	 * remove the ith WorkEntry stored in this
	 * if removal results in node 
	 */
	public WorkEntrySearchNode del(int i) {
		if(data==null||i>=data.length) {
			throw new IndexOutOfBoundsException("command received an invalid index");
		}
		if(data.length==1&&i==0) {
			this.data=null;
			return this.delete();
		}else {
			this.data[i]=null;
			WorkEntry[] temp=new WorkEntry[data.length-1];
			for(int j=0;j<temp.length;j++) {
				if(j<i) {
					temp[j]=data[j];
				}else {
					temp[j]=data[j+1];
				}
			}
			this.data=temp;
		}
		return this;
	}
	/**
	 * return String representation of the objects in this node
	 */
	public String getEntryData() {
		String res="";
		if(this.data==null) {
			return res;
		}
		double total=0;
		for(int i=0;i<data.length;i++) {
			res+=data[i].toString();
			res+='\n';
			total+=data[i].getTimeSpent();
		}
		double decimal=total-(int)total;
		if(decimal>=0&&decimal<0.25) {
			decimal=0;
		}
		else if(decimal>=0.25&&decimal<0.75) {
			decimal=0.5;
		}
		else {
			decimal=1.0;
		}
		if(total!=0) {
		  total=(int)total+decimal;
		  res+="Total: "+total+" h";
		}
		return res;
	}
	/**
	 *  return string representing a level order traversal
	 */
	public String getByRecent() {
		String res="";
		Queue<WorkEntrySearchNode> q=new Queue<WorkEntrySearchNode>();
		q.enqueue(this);
		while(q.size()!=0) {
			WorkEntrySearchNode temp=q.dequeue();
			res+=temp.activity+'\n';
			if(temp.left!=null) {
				q.enqueue(temp.left);
			}
			if(temp.right!=null) {
				q.enqueue(temp.right);
			}
		}
		return res;
	}
	/**
	 * splay the tree in different cases
	 */
	private void splay() {
		while(this.parent!=null) {
		  //child of root
		  if(this.parent!=null&&this.parent.parent==null) {
			//left child of root
			if(this.parent.compareTo(this)>0) {
				this.rotateRight();
			}
			//right child of root
			else {
				this.rotateLeft();
			}
		  }
		  //has parent and grandparent
		  else if(this.parent!=null&&this.parent.parent!=null){
			//right child  
			if(this.parent.right==this) {
				//right child of a right child
			   	if(this.parent.parent.right==this.parent) {
			   		//zig-zig
			   		this.parent.rotateLeft();
			   		this.rotateLeft();
			   	}
			   	//right child of a left child
			   	else if(this.parent.parent.left==this.parent){
			   		//zig-zag
			   		this.rotateLeft();
			   		this.rotateRight();
			   	}
			}
			//left child
			else if(this.parent.left==this) {
				//left child of a left child
			   	if(this.parent.parent.left==this.parent) {
			   		//zig-zig
			   		this.parent.rotateRight();
			   		this.rotateRight();
			   	}
			   	//right child of a left child
			   	else if(this.parent.parent.right==this.parent){
			   		//zig-zag
			   		this.rotateLeft();
			   		this.rotateRight();
			   	}
			}
		  }
	   }
	}
	/**
	 * performs a single left rotation to this node
	 */
	private void rotateLeft() {
		if(this.parent==null) {
			return;
		}
		WorkEntrySearchNode parent=this.parent;
		parent.right=this.left;
		if(this.left!=null) {
			this.left.parent=parent;
		}
		this.parent=parent.parent;
		if(parent.parent!=null) {
			if(parent==parent.parent.left) {
				parent.parent.left=this;
			}else {
				parent.parent.right=this;
			}
		}
		this.left=parent;
		parent.parent=this;
	} 
	/**
	 * performs a single right rotation to this node
	 */
	private void rotateRight() {
		if(this.parent==null) {
			return;
		}
		WorkEntrySearchNode parent=this.parent;
		parent.left=this.right;
		if(this.right!=null) {
			this.right.parent=parent;
		}
		this.parent=parent.parent;
		if(parent.parent!=null) {
			if(parent==parent.parent.left) {
				parent.parent.left=this;
			}else {
				parent.parent.right=this;
			}
		}
		this.right=parent;
		parent.parent=this;
	}
	/**
	 * helps inorder traverse
	 */
    private void toString_helper(WorkEntrySearchNode root,StringBuilder res) {
    	if(root==null) {
    		return;
    	}
    	toString_helper(root.left,res);
    	res.append(root.activity);
    	res.append('\n');
    	toString_helper(root.right,res);
    }
    /**
	 * helps inorder traverse the tree and add parenthesis
	 */
    private void structure_helper(WorkEntrySearchNode root,StringBuilder res) {
    	if(root==null) {
    		return;
    	}
    	res.append("(");
    	structure_helper(root.left,res);
    	res.append(root.activity);
    	structure_helper(root.right,res);
    	res.append(")");
    }
    /**
	 * helps delete this node in splay tree
	 */
    private WorkEntrySearchNode delete() {
    	this.splay();
    	if(this.left==null&&this.right==null) {
    		return null;
    	}
    	if(this.left==null) {
    		return this.right;
    	}
    	WorkEntrySearchNode left_max=this.predecessor();
    	WorkEntrySearchNode v_right=this.right;
    	left_max.splay();
    	left_max.right=v_right;
    	if(v_right!=null) {
    		v_right.parent=left_max;
    	}
    	return left_max;
    }
    /**
	 * helps find predecessor of this node, the max of this.left 
	 */
    private WorkEntrySearchNode predecessor() {
    	if(this.left!=null) {
    		return this.left.maximum();
    	}
    	WorkEntrySearchNode res=this.parent;
    	WorkEntrySearchNode temp=this;
    	while(res!=null&&temp==res.left) {
    		temp=res;
    		res=res.parent;
    	}
    	return res;
    }
    /**
	 * helps find maximum of the BST rooted at this node 
	 */
    private WorkEntrySearchNode maximum() {
    	WorkEntrySearchNode res=this;
    	while(res.right!=null) {
    		res=res.right;
    	}
    	return res;
    }
}
