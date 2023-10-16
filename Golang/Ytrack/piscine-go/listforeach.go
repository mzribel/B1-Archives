package piscine

func ListForEach(l *List, f func(*NodeL)) {
	if l == nil {
		return
	}
	curr := l.Head
	for curr != nil {
		f(curr)
		curr = curr.Next
	}
}
