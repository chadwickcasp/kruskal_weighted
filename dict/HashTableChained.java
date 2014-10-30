/* HashTableChained.java */

package dict;
import list.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {

  /**
   *  Place any data fields here.
   **/

  protected SList[] table;
  protected int num_buckets; //number of buckets
  protected int size; //number of entries


  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
    boolean flag = false;
    double dub_num_buckets = (double) sizeEstimate * 1.25; //this will give a load factor ~.8
    num_buckets = (int) java.lang.Math.floor(dub_num_buckets); 
    while (isPrime(num_buckets) == false){
      num_buckets++;
      }
    table = new SList[num_buckets];
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    num_buckets = 101;
    table = new SList[num_buckets];
  }

  /* isPrime determines whether an input integer is prime or not.*/
  private static boolean isPrime(int number){
    for (int i = 2; i < number ; i++){
      if (number % i == 0){
        return false;
      }
    }
    return true;
  }
  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
  int inner = (53 * code + 1) % 29986577;
  int val = inner % num_buckets;
  if (val < 0){
      val += num_buckets;
    }
    return val;
  }
  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() {
    return this.size;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    if (this.size == 0) {
      return true;
    }
    else{
      return false;
    }
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    Entry new_entry = new Entry();
    new_entry.key = key;
    new_entry.value = value;
    int hash_code = key.hashCode();
    int comp_code = this.compFunction(hash_code);

    //if there is no entry for that comppressed code:
    if (this.table[comp_code] == null){
      SList new_entry_list = new SList();
      this.table[comp_code] = new_entry_list;
      new_entry_list.insertFront(new_entry);
    }
    else{
      SList existing_list = this.table[comp_code];
      existing_list.insertBack(new_entry);  
    }
    this.size ++;
    return new_entry;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key anad an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {

    try{
      int hash_code = key.hashCode();
      int comp_code = this.compFunction(hash_code);

      if (this.table[comp_code]==null){
        return null;
      }
      else{
        SList list_at_code = this.table[comp_code];
        int list_size = list_at_code.length();
        if (list_size == 1){
          Object entry_object = list_at_code.front().item();
          Entry entry = (Entry) entry_object;
          if(entry.key().equals(key)){
            return entry;
          }
          else{
            return null;
          }
        }
        else if (list_size == 0){
          return null;
        }
        else{
          ListNode current = list_at_code.front();
          while(current != list_at_code.back()){
            Object current_entry_object = current.item();
            Entry current_entry = (Entry) current_entry_object;
            if (current_entry.key().equals(key)){
              return current_entry;
            }
            else{
              current = current.next();
            }
          }
          Object back_entry_object = current.item();
          Entry back_entry = (Entry) back_entry_object;
          if (back_entry.key().equals(key)){
            return back_entry;
          }
        }
      }
    }
    catch (InvalidNodeException | NullPointerException e){
      return null;
    }
    return null;
  }

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
    try{
      int hash_code = key.hashCode();
      int comp_code = this.compFunction(hash_code);

      if (this.find(key) == null){
        return null;
      }

      else{
        SList found_list = this.table[comp_code];
        SListNode current = (SListNode) found_list.front();

        if (found_list.length() == 1){
          this.table[comp_code] = null;
          this.size--;
          return (Entry) current.item();
        }
        else{
          while(current != found_list.back()){
              Object current_entry_object = current.item();
              Entry current_entry = (Entry) current_entry_object;
              if (current_entry.key().equals(key)){
                current.remove();
                this.size--;
                return current_entry;
              }
              else{
                current =  (SListNode) current.next();
              }
            }
            Object back_entry_object = current.item();
            Entry back_entry = (Entry) back_entry_object;
            if (back_entry.key().equals(key)){
              this.size--;
              return back_entry;
            }
        }
      }
    }
    catch (InvalidNodeException | NullPointerException e){
      return null;
    }
    return null;
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    for (int i = 0; i < num_buckets; i++){
      this.table[i] = null;
    }
    this.size = 0;
  }

  /*tests the hashCode() function by printing out a histogram of the number
  of entries in each bucket*/

  public void histPrint(){
    double n = this.size;
    double bigN = this.num_buckets;
    double useful = 1-(1/bigN);
    double expected_collisions = n - bigN + (bigN * (Math.pow(useful, n)));

    String hist = "";
    int num_collisions = 0;
    for (int i = 0; i < this.num_buckets; i++){
      int hist_num = 0;
      if (this.table[i] == null){
        hist_num = 0;
      }
      else{
        hist_num = this.table[i].length();
        num_collisions = num_collisions + (hist_num - 1);

      }
      hist = hist + "[" + hist_num + "]";  
    }
    System.out.println(hist);
    System.out.println("Expected number of collisions : " + expected_collisions);
    System.out.println("Total number of collisions : " + num_collisions);

  }


 /* Main */
  public static void main(String[] args){
   //  HashTableChained h = new HashTableChained(1000);
   //  Integer key1 = new Integer(5);
   //  String value1 = "Rocks";
   //  //String key2 = "butt";
   //  //String value2 = "face";
   //  String key3 = "David";
   //  String value3 = "Sucks";
   //  h.insert(key1, value1);
   // // h.insert(key2, value2);
   //  h.insert(key3, value3);

   //  System.out.println(h.size);


   //  Entry notfound = h.find(new Integer(4));
   //  System.out.println("Should be null : " + notfound);

   //  Entry found1 = h.find("David");
   //  System.out.println("Should be Sucks : " + found1.value());

   //  Entry removed1 = h.remove("David");
   //  System.out.println("Should be Sucks : " + removed1.value());

   //  System.out.println(h.size);

   //  Entry found2 = h.find("David");
   //  System.out.println("Should be null : " + found2);

    // Entry removed2 = h.remove("David");
    // System.out.println("Should be Sucks : " + removed2.value());

    // System.out.println("Should be null : " + h.find("David"));

    // int hash_code = key3.hashCode();
    // int comp_code = h.compFunction(hash_code);
    // System.out.println("Should be null : " + h.table[comp_code]);


  }

}
