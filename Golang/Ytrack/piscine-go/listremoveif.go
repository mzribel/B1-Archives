package piscine

func ListRemoveIf(l *List, data_ref interface{}) {
	if l == nil {
		return
	}
	var prev *NodeL
	var curr = l.Head

	for curr != nil {
		if curr.Data == data_ref {
			if curr == l.Head {
				l.Head = curr.Next
			} else if curr == l.Tail {
				prev.Next = nil
				l.Tail = prev
			} else {
				prev.Next = curr.Next
				curr = curr.Next
				continue
			}
		}
		prev = curr
		curr = curr.Next
	}
}
