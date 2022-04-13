import java.util.NoSuchElementException;

public class ShowList {
    private ShowNode head;
    private int size;

    /**
     * Default constructor
     */
    public ShowList() {
        this.head = null;
        this.size = 0;
    }

    /**
     * Copy constructor
     *
     * @param showList show list object which is to be copied
     */
    public ShowList(ShowList showList) {
        this.head = showList.getHead();
        this.size = showList.getSize();
    }

    /**
     * Adds a new tv show to the start of the list.
     *
     * @param tvShow The tv show object that is to be added.
     */
    public void addToStart(TVShow tvShow) {
        ShowNode newNode = new ShowNode(tvShow, this.head);
        this.head = newNode;
        this.size++;
    }

    /**
     * Insert element at a certain point in the linked list.
     *
     * @param tvShow The tv show object that has to be inserted.
     * @param index  The point in the linked-list at which the new element is to be inserted.
     * @throws NoSuchElementException Thrown when invalid index is passed.
     */
    public void insertAtIndex(TVShow tvShow, int index) throws NoSuchElementException {
        if (index < 0 || index > (size - 1)) throw new NoSuchElementException();
        // Iterate to the element before the required element in linked list
        ShowNode insertionPoint = this.head;
        for (int i = 1; i < index; i++) {
            insertionPoint = insertionPoint.getNextNode();
        }
        // Create new node and make it point to the next element in the list
        ShowNode newNode = new ShowNode(tvShow, insertionPoint.getNextNode());
        // Make the previos node point to the new node
        insertionPoint.setNextNode(newNode);
        this.size++;
    }

    /**
     * Delete a show node from a specific point in the linked list
     *
     * @param index The index of the element to be deleted.
     */
    public void deleteFromIndex(int index) {
        if (index < 0 || index > (size - 1)) throw new NoSuchElementException();
        if (this.size == 0) return;
        // If first element is to be deleted then set next element as head
        if (index == 0) {
            this.head = this.head.getNextNode();
            return;
        }
        // Iterate to the node right before deletion point
        ShowNode deletionPoint = this.head;
        for (int i = 1; i < index; i++) {
            deletionPoint = deletionPoint.getNextNode();
        }
        // Make the previous node point to the node after the deletion-point
        deletionPoint.setNextNode(deletionPoint.getNextNode().getNextNode());
    }

    /**
     * Delete the first item in list
     */
    public void deleteFromStart() {
        this.deleteFromIndex(0);
    }

    /**
     * Replace element at a certain point in the list.
     *
     * @param newShow The new show which is to be placed.
     * @param index   The point in the list where the show is to be replaced.
     */
    public void replaceAtIndex(TVShow newShow, int index) {
        if (index < 0 || index > (size - 1)) return;
        if (index == 0) {
            this.head.setTvShow(newShow);
            return;
        }
        ShowNode currentNode = this.head;
        for (int i = 0; i <= index; i++) {
            currentNode = currentNode.getNextNode();
        }
        currentNode.setTvShow(newShow);
    }

    /**
     * Find and retrieve a show by id.
     *
     * @param showID Id of the show to be retrieved.
     * @return Returns pointer to the show object if found, else returns null.
     */
    public ShowNode find(String showID) {
        ShowNode currentNode = this.head;
        int iterations = 0;
        while (currentNode != null && currentNode.getNextNode() != null) {
            iterations++;
            if (currentNode.getTvShow().getShowID().equals(showID)) {
                System.out.println(String.format("Found show after %d iterations.", iterations));
                return currentNode;
            }

            currentNode = currentNode.getNextNode();
        }
        System.out.println("Show not found after " + iterations + " iterations.");
        return null;
    }

    /**
     * Overloaded find method. Find by TVShow object
     *
     * @param show TVShow object that is to be found.
     * @return Pointer to the show object or null.
     */
    public ShowNode find(TVShow show) {
        ShowNode currentNode = this.head;
        while (currentNode != null && currentNode.getNextNode() != null) {
            if (currentNode.getTvShow().equals(show)) return currentNode;
            currentNode = currentNode.getNextNode();
        }
        return null;
    }

    /**
     * Verify if a show with provided id exists in the list.
     *
     * @param showID The id which is to be found.
     * @return Returns true if show exists, else false.
     */
    public boolean contains(String showID) {
        return (this.find(showID) == null) ? false : true;
    }

    /**
     * Overloaded contains method.
     *
     * @param show Show object to be searched.
     * @return True if show exists in list, else false.
     */
    public boolean contains(TVShow show) {
        return (this.find(show) == null) ? false : true;
    }

    /**
     * Check if two show list objects are equal. They are considered equal if every show in one list is present in
     * the other and vice versa.
     *
     * @param showList List to be compared with.
     * @return Returns true if they are equal, else false.
     */
    public boolean equals(ShowList showList) {
        ShowNode currNode = this.head;
        if (this.getSize() != showList.getSize()) return false;
        while (currNode.getNextNode() != null) {
            if (!showList.contains(currNode.getTvShow())) return false;
            currNode = currNode.getNextNode();
        }
        return true;
    }

    /**
     * Class implements each node in the linked list.
     * Contains the TV show object for that node and a pointer to the next node in the linked list.
     */
    public class ShowNode {
        private TVShow tvShow;
        private ShowNode nextNode;

        /**
         * Parameterized constructor
         *
         * @param tvShow
         * @param nextNode
         */
        public ShowNode(TVShow tvShow, ShowNode nextNode) {
            this.tvShow = tvShow;
            this.nextNode = nextNode;
        }

        /**
         * Default constructor
         */
        public ShowNode() {
            this(null, null);
        }

        /**
         * Copy constructor
         *
         * @param nextNode
         */
        public ShowNode(ShowNode nextNode) {
            this(nextNode.tvShow, nextNode.nextNode);
        }

        /**
         * Creates and returns a clone of the object show node object
         *
         * @return Cloned show-node object
         */
        public ShowNode clone() {
            return new ShowNode(this);
        }

        public TVShow getTvShow() {
            return tvShow;
        }

        public void setTvShow(TVShow tvShow) {
            this.tvShow = tvShow;
        }

        public ShowNode getNextNode() {
            return nextNode;
        }

        public void setNextNode(ShowNode nextNode) {
            this.nextNode = nextNode;
        }
    }

    public ShowNode getHead() {
        return head;
    }

    public void setHead(ShowNode head) {
        this.head = head;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
