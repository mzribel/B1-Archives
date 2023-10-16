package piscine

func ListReverse(l *List) {
	if l.Head.Next == nil {
		return
	}
	var prev *NodeL
	var next *NodeL
	curr := l.Head
	l.Tail = curr

	for curr != nil {
		next = curr.Next
		curr.Next = prev
		prev = curr
		curr = next
	}
	l.Head = prev
}
