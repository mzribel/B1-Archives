package piscine

func ListPushFront(l *List, data interface{}) {
	newnode := NodeL{
		Data: data,
		Next: nil,
	}

	if l.Tail == nil {
		l.Head = &newnode
		l.Tail = &newnode
		return
	}

	newnode.Next = l.Head
	l.Head = &newnode
}
