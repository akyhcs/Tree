package com.tree.prac;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Treeutil {
    public static Tree root = null;
	public final static Predicate<Tree> isRootNull = (r)->r==null;
	public final static Predicate<Tree> isChildNode = (r)->r.getLeftChild()==null&&r.getRightChild()==null;
	public static void main(String[] args)
	{
		Supplier<Integer> s = ()->{ return (int)(Math.random()*(99-10+1)+10);};
		
		int n = s.get();
		System.out.println("n----------"+n);
		int k = 0;
		int arr[] =
			{10 ,11 ,13 
					,14 ,16 ,17 ,18 ,19 ,21 ,22 ,23 ,
					24 ,25 ,26 ,28 ,29 ,32 ,34 ,35 ,36 ,39 ,41 ,42 ,44 ,45
					,46 ,47 ,48 ,49 ,50 ,53 ,55 ,57 ,58 ,59 ,60 ,62 ,63 ,65 
					,66 ,68 ,69 ,70 ,71 ,72 ,73 ,74 ,75 ,76 ,77 ,78 ,80 ,81 ,83 ,84 ,85 ,86 ,87 };
//					88 ,89 ,91 ,92 ,94 ,98 ,99 };
		int arrbstDLL[] = {1000,900,2000,3000,800,950,940,960,955,945}; 
		for(int i =0;i<arrbstDLL.length;i++)
		{
			root = insert(Treeutil.root,arrbstDLL[i]);
			
		}
		System.out.println(k+"--------------------");
		Consumer<Tree> displayTree = (root)->{
												inorder(root);System.out.println();
												};
		displayTree.accept(root);
		Function<Tree,Integer> treeSize = (root)->{return findTreeSize(root);};
		System.out.println("****"+treeSize.apply(root)+"****"+arr.length);
		if(treeSize.apply(root)==n)
		{
			System.out.println("size is correct "+n);
		}
		Tree start = bst_to_DLL(root);
		Consumer<Tree> printnodes  = printNodes(start);
		printnodes.accept(start);
		
	}
	private static Consumer<Tree> printNodes(Tree start) {
		Consumer<Tree> printnodes = (t)->{
			t.setLeftChild(null);
			while(t!=null)
			{
				System.out.print(t.getData()+" ,");
				t = t.getRightChild();
			}
			System.out.println();
		};
		return printnodes;
		
	}
	private static Tree bst_to_DLL(Tree root) {
		Tree leftDLL = null,rightDLL = null,
		startLeftDll=null,startRightDll=null,endLeftDll=null,endRightDll=null;
		Tree leftChild = root.getLeftChild();
		Tree rightChild = root.getRightChild();
		if(isRootNull.negate().test(leftChild))
		{
			leftDLL = bst_to_DLL(leftChild);
			if(isChildNode.test(leftDLL))
			{
				startLeftDll=endLeftDll=leftDLL;
			}
			else
			{
				startLeftDll = leftDLL;
				endLeftDll = leftDLL.getLeftChild();
				
			}
		}
		if(isRootNull.negate().test(rightChild))
		{
			rightDLL = bst_to_DLL(rightChild);
			if(isChildNode.test(rightDLL))
			{
				startRightDll=endRightDll=rightDLL;
			}
			else
			{
				startRightDll = rightDLL;
				endRightDll = rightDLL.getLeftChild();
				
			}
		}
		if(isRootNull.test(startLeftDll)==false&&isRootNull.test(startRightDll)==false)
		{
			endLeftDll.setRightChild(root);
			root.setLeftChild(endLeftDll);
			startRightDll.setLeftChild(root);
			root.setRightChild(startRightDll);
			startLeftDll.setLeftChild(endRightDll);
			return leftDLL;
		}
		else if(isRootNull.test(startLeftDll)==true&&isRootNull.test(startRightDll)==false)
		{
			startRightDll.setLeftChild(root);
			root.setRightChild(startRightDll);
			root.setLeftChild(endRightDll);
			return root;
		}
		else if(isRootNull.test(startLeftDll)==false&&isRootNull.test(startRightDll)==true)
		{
			endLeftDll.setRightChild(root);
			root.setLeftChild(endLeftDll);
			startLeftDll.setLeftChild(root);
			return leftDLL;
		}
		//leaf node
		return root;
	}
	private static Integer findTreeSize(Tree root) {
		
		return isRootNull.negate().test(root)?(1+findTreeSize(root.getLeftChild())+findTreeSize(root.getRightChild())):0;
	}
	private static void inorder(Tree node) {
		if(isRootNull.test(node))
		{
			return;
		}
		inorder(node.getLeftChild());
		System.out.print(node.getData()+" ,");
		inorder(node.getRightChild());
	}
	private static Tree insert(Tree node, Integer integer) {
		
		if(isRootNull.test(node))
		{
			node = new Tree(null,null,integer);
		}
		else if(node.getData()>integer)
		{
			node.setLeftChild(insert(node.getLeftChild(), integer));
		}
		else if(node.getData()<integer)
		{
			node.setRightChild(insert(node.getRightChild(), integer));
		}
		return node;
	}
	
}
class Tree
{
	private Tree leftChild;
	private Tree rightChild;
	private int data;
	
	public Tree() {
		super();
	}
	public Tree(Tree leftChild, Tree rightChild, int data) {
		super();
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.data = data;
	}
	@Override
	public int hashCode() {
		return Objects.hash(data, leftChild, rightChild);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tree other = (Tree) obj;
		return data == other.data && Objects.equals(leftChild, other.leftChild)
				&& Objects.equals(rightChild, other.rightChild);
	}
	public Tree getLeftChild() {
		return leftChild;
	}
	public void setLeftChild(Tree leftChild) {
		this.leftChild = leftChild;
	}
	public Tree getRightChild() {
		return rightChild;
	}
	public void setRightChild(Tree rightChild) {
		this.rightChild = rightChild;
	}
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	
}
