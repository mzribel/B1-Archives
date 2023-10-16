package piscine

func SortListInsert(l *NodeI, data_ref int) *NodeI {
	if l == nil {
		return l
	}

	newnode := &NodeI{Data: data_ref}
	curr := l

	if l.Data >= newnode.Data {
		newnode.Next = l
		return newnode
	}
	for curr.Next != nil && curr.Next.Data < newnode.Data {
		curr = curr.Next
	}
	newnode.Next = curr.Next
	curr.Next = newnode
	return l
}
