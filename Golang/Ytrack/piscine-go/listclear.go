package piscine

func ListClear(l *List) {
	if l == nil {
		return
	}
	l.Head = nil
	l.Tail = nil
}
