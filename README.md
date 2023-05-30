# Address-Book
The Address Book Manager is a Java program that utilizes Java generics and Red-Black trees to efficiently manage an address book. It provides a reliable and scalable solution for storing and retrieving contact information in a sorted and balanced manner.

### Red-Black Trees
The use of Red-Black trees is a crucial aspect of the Address Book Manager program. Red-Black trees are self-balancing binary search trees that maintain a balance between efficient search, insert, and delete operations.  

**Here are some key benefits of using Red-Black trees:**  

Balanced Structure: Red-Black trees ensure the tree remains balanced, maintaining a maximum height of O(log n). This property guarantees efficient search operations with a logarithmic time complexity. 

Insertion and Deletion Efficiency: Red-Black trees perform insertions and deletions with a guaranteed worst-case time complexity of O(log n). This ensures that adding or removing contacts from the address book remains efficient, regardless of its size.   

Ordered Retrieval: Red-Black trees automatically maintain an ordered structure based on the contact names. This property allows for efficient retrieval of contacts in sorted order, facilitating operations like alphabetical listing or searching for specific names.  

By utilizing Red-Black trees, the Address Book Manager achieves a balance between efficiency, scalability, and maintaining a sorted structure, making it an ideal choice for managing address books.  
