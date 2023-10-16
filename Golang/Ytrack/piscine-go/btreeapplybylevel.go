package piscine

func BTreeApplyByLevel(root *TreeNode, f func(...interface{}) (int, error)) {
	if root == nil {
		return
	}
	queue := []TreeNode{}

	queue = append(queue, *root)
	var curr TreeNode
	for len(queue) > 0 {
		curr = queue[0]
		queue = queue[1:]

		if curr.Left != nil {
			queue = append(queue, *curr.Left)
		}
		if curr.Right != nil {
			queue = append(queue, *curr.Right)
		}
		f(curr.Data)
	}
}
