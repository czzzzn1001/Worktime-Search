package main;
/**
 * Explanation: Implementation of WorkTimeAnalysisTool
 * Known Bugs:None
 * Zheng Chu
 * zhengchu@brandeis.edu
 * Nov 7, 2020
 * COSI 21A PA2
 */
public class WorkTimeAnalysisTool {
	WorkEntrySearchNode root;
	boolean searched;
	boolean bingo;
	/**
	 * constructor of this class
	 */
	public WorkTimeAnalysisTool(WorkEntry[] entries) {
		root=null;
		searched=false;
		bingo=true;
		for(int i=0;i<entries.length;i++) {

			String temp_act=entries[i].getActivity();
			WorkEntrySearchNode temp_node=new WorkEntrySearchNode(temp_act);
			if(i==0) {
				root=temp_node;
			}
			root=root.insert(temp_node);
			//each insertion changes the root, even node is already there
			root.add(entries[i]);
			//add WorkEntry to the node with same activity
		}
	}
	/**
	 * parse given command
	 * @return result of list, search or del
	 */
	public String parse(String cmd) {
		String res="";
		if(cmd.length()==0) {
			return res;
		}
		String[] com=cmd.split(" ");
		if(com[0].equals("list")) {
			if(com[1].equals("r")) {
				res=root.getByRecent();
			}
			else if(com[1].equals("l")) {
				res=root.toString();
			}
		}
		else if(com[0].equals("search")) {
			if(com[1].length()>=2) {
				String search=com[1].substring(1,com[1].length()-1);
				//get rid of the quotation marks
				WorkEntrySearchNode search_node=new WorkEntrySearchNode(search);
				root=root.search(search_node);
				res=root.getEntryData();
				searched=true;
				bingo=(search.equals(root.activity));
			}
		}
		else if(com[0].equals("del")) {
			if(searched==false||bingo==false) {
				throw new IllegalStateException("[del] command has no entries to index");
			}
			int index=com[1].charAt(0)-'0';
			root=root.del(index);
		}
		return res;
	}
	
	
	
}
