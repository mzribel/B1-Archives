package piscine

func ListAt(l *NodeL, pos int) *NodeL {
	if l == nil || l.Next == nil {
		return nil
	}

	position := 1
	curr := l.Next

	for position < pos {
		if curr.Next == nil {
			return nil
		}
		curr = curr.Next
		position++
	}
	return curr
}
