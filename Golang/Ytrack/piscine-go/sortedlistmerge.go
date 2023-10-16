package piscine

func SortedListMerge(n1 *NodeI, n2 *NodeI) *NodeI {
	if n1 == nil || n2 == nil {
		return nil
	}
	curr := n2
	for curr != nil {

		n1 = SortListInsert(n1, curr.Data)
		curr = curr.Next
	}
	return n1
}
