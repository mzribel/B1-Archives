package piscine

func ListFind(l *List, ref interface{}, comp func(a, b interface{}) bool) *interface{} {
	if l == nil {
		return nil
	}
	curr := l.Head
	for curr != nil {
		if comp(curr.Data, ref) {
			return &curr.Data
		}
		curr = curr.Next
	}
	return nil
}
