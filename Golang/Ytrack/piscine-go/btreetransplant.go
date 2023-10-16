package piscine

func BTreeTransplant(root, node, rplc *TreeNode) *TreeNode {
	if node.Parent == nil {
		root = rplc
		return root
	}
	if node.Parent.Left == node {
		node.Parent.Left = rplc
	}
	if node.Parent.Right == node {
		node.Parent.Right = rplc
	}
	if rplc != nil {
		rplc.Parent = node.Parent
	}
	return root
}
