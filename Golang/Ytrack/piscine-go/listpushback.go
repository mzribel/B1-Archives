package piscine

type NodeL struct {
	Data interface{}
	Next *NodeL
}

type List struct {
	Head *NodeL
	Tail *NodeL
}

func ListPushBack(l *List, data interface{}) {
	newnode := NodeL{
		Data: data,
		Next: nil,
	}

	if l.Tail == nil {
		l.Head = &newnode
		l.Tail = &newnode
		return
	}

	l.Tail.Next = &newnode
	l.Tail = &newnode
}
