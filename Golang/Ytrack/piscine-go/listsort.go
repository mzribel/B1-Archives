package piscine

type NodeI struct {
	Data int
	Next *NodeI
}

func ListSort(l *NodeI) *NodeI {
	if l == nil || l.Next == nil {
		return l
	}

	var next *NodeI

	// Counting nodes:
	var curr = l
	length := 0
	for curr != nil {
		length++
		curr = curr.Next
	}

	// Sorting with bubble sort:
	for i := 0; i < length; i++ {
		curr = l
		next = curr.Next
		for next != nil {

			if CompareInt(curr.Data, next.Data) == 1 {
				curr.Data, next.Data = next.Data, curr.Data
			}
			curr = next
			next = curr.Next
		}
	}
	return l
}
