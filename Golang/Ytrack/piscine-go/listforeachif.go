package piscine

func ListForEachIf(l *List, f func(*NodeL), cond func(*NodeL) bool) {
	if l == nil {
		return
	}
	curr := l.Head
	for curr != nil {
		if cond(curr) {
			f(curr)
		}
		curr = curr.Next
	}
}
