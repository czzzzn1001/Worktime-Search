package main;
/**
 * Explanation: Implementation of Queue
 * Known Bugs:None
 * Zheng Chu
 * zhengchu@brandeis.edu
 * Nov 7, 2020
 * COSI 21A PA2
 */
public class Queue<T> {
    int front;
    int rear;
    int size;  //size of Queue
    int n; // size of array
    T[] Q;
    /**
	 * constructor of Queue
	 */
	public Queue() {
		n=20;
		front=0;
		size=0;
		rear=0;
		Q=(T[]) new Object[n];
	}
	/**
	 * judges if this queue is full
	 */
	public boolean full() {
		return this.size==this.n;
	}
	/**
	 * enqueue data
	 */
	public void enqueue(T data) {
		if(this.full()) {
			//resize the Q array and copy, reassign the front and rear
			n+=20;
			T[] temp=(T[]) new Object[n];
			int j=front;
			for(int i=0;i<size;i++) {
				temp[i]=Q[j%(n-20)];//n-20, the original n
				j++;
			}
			Q=temp;
			front=0;
			rear=size; //the next index of current end, which is size-1+1
		}
		Q[rear]=data;
		rear=(rear+1)%n;
		size++;
	}
	/**
	 * dequeue from the front of queue
	 */
	public T dequeue() {
		if(this.size==0) {
			return null;
		}
		T res=Q[front];
		front=(front+1)%n;
		size--;
		return res;
	}
	/**
	 * return front of queue
	 */
	public T front() {
		if(this.size==0) {
			return null;
		}
		return Q[front];
	}
	/**
	 * return size of queue
	 */
	public int size() {
		return this.size;
	}
	public void main() {
		
	}
}
