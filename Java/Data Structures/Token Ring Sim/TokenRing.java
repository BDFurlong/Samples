package TokenRing;
import java.util.*;


public class TokenRing<E> {
	
	private static class NetworkNode<E>{
		
		private E data;
		private NetworkNode<E>next = null;
		
		public NetworkNode(E data, NetworkNode<E> next){
			this.data = data;
			this.next = next;
		}
		
		public NetworkNode(E data){
			this(data, null);
		}
	}
	
	private NetworkNode<E> head = null;
	
	private int size = 0;
	
	public E get(int index){
		if(index < 0 || index >= size){
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}
		NetworkNode<E> node = getNode(index);
		return node.data;
	}


	public E set(int index, E newValue){
		if(index < 0 || index >= size){
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}
		NetworkNode<E> node = getNode(index);
		E result = node.data;
		node.data = newValue;
		return result;
	}


	public void add(int index, E item){
		if(index < 0 || index > size){
			throw new IndexOutOfBoundsException(Integer.toString(index));
		}
		if(index == 0){
			addFirst(item);
		} else {
			NetworkNode<E> node = getNode(index - 1);
			addAfter(node, item);
		}
	}


	private void addFirst(E item){
		head = new NetworkNode<E>(item, head);
		size++;
	}

	
	private void addAfter(NetworkNode<E> node, E item){
		node.next = new NetworkNode<E>(item, node.next);
		size++;
	}
	
	private E removeFirst(){
		NetworkNode<E> temp = head;
		if(head != null){
			head = head.next;
		}
		if(temp != null){
			size--;
			return temp.data;
		} else{
			return null;
		}
		}
	
	private NetworkNode<E> getNode(int index){
		NetworkNode<E> node = head;
		for(int i = 0; i < index && node != null; i++){
			node = node.next;
		}
		return node;
	}
		
	int size(){
		return size;
	}

	private class Token implements Iterator<E>{
			
			private NetworkNode<E> nextItem;
			private NetworkNode<E> lastItemReturned;
			private int index = 0;
			

			
			public Token(int i){
				if( i < 0 || i > size){
					throw new IndexOutOfBoundsException("Invalid Index" + 1);
				}
				lastItemReturned = null;
				if( i == size){
					index = size;
					nextItem = null;	
				}else{
					nextItem = head;
					for(index = 0; index < i; index++){
						nextItem = nextItem.next;
					}
				}
				
			}
			
			@Override
			public boolean hasNext() {
				return nextItem != null;
			}

			@Override
			public E next() {
				if(!hasNext()){
					throw new NoSuchElementException();
				}
				lastItemReturned = nextItem;
				nextItem = nextItem.next;
				index++;
				return lastItemReturned.data;
			}



			@Override
			public void remove() {
				if(!hasNext()){
					lastItemReturned = nextItem;
					index--;
				}
				}
				
				}
			

			
		
				
	}
	

	
	

