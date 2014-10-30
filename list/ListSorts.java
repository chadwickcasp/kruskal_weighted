/* ListSorts.java */

//import list.*;

package list;

public class ListSorts {

  private final static int SORTSIZE = 1000000;

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {
    int size = q.size();
    LinkedQueue queue_of_queues = new LinkedQueue();
    for (int i = 0; i < size; i++){
      Object item = null;
      try{
        item = q.dequeue();
      }
      catch (QueueEmptyException e){
        System.out.println("Exception caught in makeQueueOfQueues()");
      }
      LinkedQueue indi_queue = new LinkedQueue();
      indi_queue.enqueue(item);
      queue_of_queues.enqueue(indi_queue);
    }
    return queue_of_queues;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {

    LinkedQueue out = new LinkedQueue();
    Object item1 = null;
    Object item2 = null;

    int flag = 0;
    int iterate = 0;

    while(!q1.isEmpty() && !q2.isEmpty()){
      try{
        item1 = q1.front();
        item2 = q2.front();

        int compare = ((Comparable) item1).compareTo((Comparable) item2);
        if (compare > 0){
          q2.dequeue();
          out.enqueue(item2);
        }
        else if (compare < 0){
          q1.dequeue();
          out.enqueue(item1);
        }
        else if (compare == 0){
          q1.dequeue();
          q2.dequeue();
          out.enqueue(item1);
          out.enqueue(item2);
        }
      }
      catch(QueueEmptyException e){
        System.out.println("Exception caught in mergeSortedQueues()");
      }
    }

    if (!q1.isEmpty()){
      out.append(q1);
    }
    else if (!q2.isEmpty()){
      out.append(q2);
    }

    return out;
  }


  /**
   *  partition() partitions qIn using the pivot item.  On completion of
   *  this method, qIn is empty, and its items have been moved to qSmall,
   *  qEquals, and qLarge, according to their relationship to the pivot.
   *  @param qIn is a LinkedQueue of Comparable objects.
   *  @param pivot is a Comparable item used for partitioning.
   *  @param qSmall is a LinkedQueue, in which all items less than pivot
   *    will be enqueued.
   *  @param qEquals is a LinkedQueue, in which all items equal to the pivot
   *    will be enqueued.
   *  @param qLarge is a LinkedQueue, in which all items greater than pivot
   *    will be enqueued.  
   **/   
  public static void partition(LinkedQueue qIn, Comparable pivot, 
                               LinkedQueue qSmall, LinkedQueue qEquals, 
                               LinkedQueue qLarge) {
    try{
      while(!qIn.isEmpty()){
        Object item = qIn.dequeue();
        int compare = ((Comparable) item).compareTo(pivot);
        if (compare > 0){
          qLarge.enqueue(item);
        }
        else if (compare < 0){
          qSmall.enqueue(item);
        }
        else if (compare == 0){
          qEquals.enqueue(item);
        }
      }
    }
    catch(QueueEmptyException e){
      System.out.println("Exception caught in partition()");
    }
  }



  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {
    LinkedQueue temp = makeQueueOfQueues(q);
    LinkedQueue q1 = null;
    LinkedQueue q2 = null;
    while (temp.size() > 1){
      try{
        q1 = (LinkedQueue) temp.dequeue();
        q2 = (LinkedQueue) temp.dequeue();
      }
      catch(QueueEmptyException e1){
        System.out.println("Exception caught in mergeSort() : 1");
      }
      LinkedQueue merged = mergeSortedQueues(q1,q2);
      temp.enqueue(merged);
    }

    try{
      q.append((LinkedQueue) temp.front());
    }
    catch(QueueEmptyException e2){
      System.out.println("Exception caught in mergeSort() : 2");
    } 
  }


  /**
   *  quickSort() sorts q from smallest to largest using quicksort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void quickSort(LinkedQueue q) {
    int size = q.size();
    int random = (int) (((size - 1) * Math.random()) + 1);
    Object pivot = q.nth(random);

    LinkedQueue qSmall = new LinkedQueue();
    LinkedQueue qLarge = new LinkedQueue();
    LinkedQueue qEquals = new LinkedQueue();

    partition(q, (Comparable) pivot, qSmall, qEquals, qLarge);

    if (!qSmall.isEmpty()){
      quickSort(qSmall);
    }
    if (!qLarge.isEmpty()){
      quickSort(qLarge);
    }

    q.append(qSmall);
    q.append(qEquals);
    q.append(qLarge);
  }

  /**
   *  makeRandom() builds a LinkedQueue of the indicated size containing
   *  Integer items.  The items are randomly chosen between 0 and size - 1.
   *  @param size is the size of the resulting LinkedQueue.
   **/
  public static LinkedQueue makeRandom(int size) {
    LinkedQueue q = new LinkedQueue();
    for (int i = 0; i < size; i++) {
      q.enqueue(new Integer((int) (size * Math.random())));
    }
    return q;
  }

  /**
   *  main() performs some tests on mergesort and quicksort.  Feel free to add
   *  more tests of your own to make sure your algorithms works on boundary
   *  cases.  Your test code will not be graded.
   **/
  // public static void main(String [] args) {

  // }

}
