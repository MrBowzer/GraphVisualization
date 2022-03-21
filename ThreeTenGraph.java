import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Hypergraph;
import edu.uci.ics.jung.graph.UndirectedGraph;

import edu.uci.ics.jung.graph.util.Pair;
import edu.uci.ics.jung.graph.util.EdgeType;

import org.apache.commons.collections15.Factory;

import java.util.ArrayList;
import java.util.Collection;

//You may use your hash map and hash set if you'd like
//or you may import the java.util versions.
//The interface  is the same for both, so the code you
//write here (in ThreeTenGraph) should be the same for
//either one!

//Uncomment the following lines if you want to use the java.util version
import java.util.HashMap; //or use ThreeTenHashMap!
//import java.util.HashSet; //or use ThreeTenHashSet!

/**
 * This class represents the graph and how it will function.
 * @author samir bawazir
 * @param <V> The genetic type that will be the vertices.
 * @param <E> The genetic type that will be the edges.
 */
class ThreeTenGraph<V extends ThreeTenGraphComponent, E extends ThreeTenGraphComponent>
	implements Graph<V, E>, UndirectedGraph<V, E> {
	
	//********************************************************************************
	//   YOUR CODE GOES IN THIS SECTION
	//********************************************************************************
	
	//**************** IMPORTANT WARNING ****************
	//Due to Java complexities with bounded genics that it would be difficult to explain here,
	//if you want an array of V[] or E[], the following format ___SHOULD NOT___ be used:
	//         V[] items = (V[]) new Object[10];
	//instead, use this format:
	//         V[] items = (V[]) new ThreeTenGraphComponent[10];


	/**
	 * This is the hashMap that will hold the vertices as the key and have an arraylist of its edges as the value.
	 */
	private HashMap<V,ArrayList<E>> verts;
	/**
	 * This is the hashmap that will hold the edges as the key and an arraylist of its vertices as its value.
	 */
	private HashMap<E,ArrayList<V>> edges;

	/**
	 * Creates a new graph. Initializing all appropriate instance variables.
	 */
	@SuppressWarnings("unchecked")
	public ThreeTenGraph() {
		verts = new HashMap<V,ArrayList<E>>(5);
		edges = new HashMap<E,ArrayList<V>>(5);
	}
	
	/**
	 * Returns a view of all edges in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees 
	 * about the ordering of the vertices within the set.
	 * @return a Collection view of all edges in this graph
	 */
	public Collection<E> getEdges() {
		System.out.println("getEdges");
		return edges.keySet();
	}

	/**
	 * Returns a view of all vertices in this graph. In general, this
	 * obeys the Collection contract, and therefore makes no guarantees
	 * about the ordering of the vertices within the set.
	 * @return a Collection view of all vertices in this graph
	 */
	public Collection<V> getVertices() {
		System.out.println("getVertices");
		return verts.keySet();
	}

	/**
	 * Returns the number of edges in this graph.
	 * @return the number of edges in this graph
	 */
	public int getEdgeCount() {
		//int numOfEgdes = 0;
		System.out.println("getEdgeCount");
		return edges.keySet().size();
	}

	/**
	 * Returns the number of vertices in this graph.
	 * @return the number of vertices in this graph
	 */
	public int getVertexCount() {
		System.out.println("getVertexCount");

		return verts.keySet().size();
	}

	/**
	 * Returns the collection of vertices in this graph which are connected to edge.
	 * Note that for some graph types there are guarantees about the size of this collection
	 * (i.e., some graphs contain edges that have exactly two endpoints, which may or may
	 * not be distinct).  Implementations for those graph types may provide alternate methods
	 * that provide more convenient access to the vertices.
	 *
	 * @param edge the edge whose incident vertices are to be returned
	 * @return the collection of vertices which are connected to edge,or null if edge is not present
	 */
	public Collection<V> getIncidentVertices(E edge) {

		System.out.println("getIncidentVertice");
		if (edges.get(edge).size() != 2) {
			return null;
		}

		return edges.get(edge);
	}

	/**
	 * Returns the collection of vertices which are connected to vertex
	 * via any edges in this graph.
	 * If vertex is connected to itself with a self-loop, then
	 * it will be included in the collection returned.
	 *
	 * @param vertex the vertex whose neighbors are to be returned
	 * @return  the collection of vertices which are connected to vertex, or null if vertex is not present
	 */
	public Collection<V> getNeighbors(V vertex) {
		System.out.println("getNeighbors");
		ArrayList<V> neigh = new ArrayList<>();
		if(verts.get(vertex) == null) {
			return null;
		} else{
			ArrayList<E> edgesOfVertex = new ArrayList<>();
			edgesOfVertex =verts.get(vertex);
			for(E vertConnectsEdge: edgesOfVertex)
			{
				for(V vertexNeighbors : edges.get(vertConnectsEdge))
				{
					neigh.add(vertexNeighbors);
				}
			}
		}

		return neigh;

	}

	/**
	 * Returns the collection of edges in this graph which are connected to vertex.
	 *
	 * @param vertex the vertex whose incident edges are to be returned
	 * @return  the collection of edges which are connected to vertex,or null if vertex is not present
	 */
	public Collection<E> getIncidentEdges(V vertex) {
		System.out.println("getIncidentEdges");
		boolean vertexInGraph = false;
		for(V n: verts.keySet())
		{
			if(n == vertex)
			{
				vertexInGraph = true;
			}
		}
		if(vertexInGraph == false)
		{
			return null;
		}
		ArrayList<E> edges = new ArrayList<>();

		for(E connected: verts.get(vertex))
		{
			edges.add(connected);
		}

		return edges ;
	}

	/**
	 * Returns an edge that connects v1 to v2.
	 * If this edge is not uniquely
	 * defined (that is, if the graph contains more than one edge connecting
	 * v1 to v2), any of these edges
	 * may be returned.  findEdgeSet(v1, v2) may be
	 * used to return all such edges.
	 * Returns null if either of the following is true:
	 * <ul>
	 * <li/>v1 is not connected to v2
	 * <li/>either v1 or v2 are not present in this graph
	 * </ul>
	 * <p>
	 *
	 * </p><b>Note</b>: for purposes of this method, v1 is only considered to be connected to
	 * v2 via a given <i>directed</i> edge e if
	 * v1 == e.getSource() && v2 == e.getDest() evaluates to true.
	 * (v1 and v2 are connected by an undirected edge u if
	 * u is incident to both v1 and v2.)
	 *
	 * @param v1 the first vertex.
	 * @param v2 the second vertex.
	 * @return  an edge that connects v1 to v2,or null if no such edge exists (or either vertex is not present)
	 * @see Hypergraph#findEdgeSet(Object, Object)
	 */
	public E findEdge(V v1, V v2) {
		System.out.println("findEdge");

		E edgeFound = null;
		if(verts.get(v1) == null || verts.get(v2) == null )
		{
			return null;
		}
		ArrayList<E> edges = new ArrayList<>();
		for(E edgesInV1 : verts.get(v1))
		{
			for (E edgesInV2 : verts.get(v2))
			{
				if(edgesInV1.equals(edgesInV2))
				{
					edgeFound = edgesInV2;
					break;
				}
			}
		}

		return edgeFound;
	}

	/**
	 * Adds edge e to this graph such that it connects
	 * vertex v1 to v2.
	 * If this graph does not contain v1, v2,
	 * or both, implementations may choose to either silently add
	 * the vertices to the graph or throw an IllegalArgumentException.
	 * If this graph assigns edge types to its edges, the edge type of
	 * e will be the default for this graph.
	 * See Hypergraph.addEdge() for a listing of possible reasons
	 * for failure. In addition, this should fail if the vertices or edge
	 * violates any given restrictions (such as invalid IDs).
	 * Equivalent to addEdge(e, new Pair (v1, v2)).
	 * @param e the edge to be added
	 * @param v1 the first vertex to be connected
	 * @param v2 the second vertex to be connected
	 * @return true if the add is successful, false otherwise
	 * @see Hypergraph#addEdge(Object, Collection)
	 * @see #addEdge(Object, Object, Object, EdgeType)
	 */
	public boolean addEdge(E e, V v1, V v2) {
		System.out.println("addEdge");

		if(verts.get(v1) == null || verts.get(v2) == null)
		{
			throw new IllegalArgumentException();
		}

		if(v1.equals(v2))
		{
			ArrayList<E> updatedList = new ArrayList<>();
			updatedList = verts.get(v1);
			updatedList.add(e);
			verts.put(v1, updatedList);
			ArrayList<V> newVerts = new ArrayList<>();
			newVerts.add(v1);
			newVerts.add(v2);
			edges.put(e, newVerts);
		}
		else {
			ArrayList<E> updatedList = new ArrayList<>();
			updatedList = verts.get(v1);
			updatedList.add(e);
			verts.put(v1, updatedList);
			updatedList = verts.get(v2);
			updatedList.add(e);
			verts.put(v2, updatedList);
			ArrayList<V> newVerts = new ArrayList<>();
			newVerts.add(v1);
			newVerts.add(v2);
			edges.put(e, newVerts);
		}
		return true;
	}

	/**
	 * Adds vertex to this graph.
	 * Fails if vertex is null or already in the graph.
	 * Also fails if the vertex violates and constraints given in
	 * the project (such as ID restrictions).
	 *
	 * @param vertex	the vertex to add
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if vertex is null
	 */
	public boolean addVertex(V vertex) {
		System.out.println("addVertex");

		if(vertex == null)
		{
			throw new IllegalArgumentException();
		}
		else if(verts.get(vertex) != null)
		{
			return false;
		}
		else
		{
			verts.put(vertex,new ArrayList<E>());
		}
		return true;
	}

	/**
	 * Removes edge from this graph.
	 * Fails if edge is null, or is otherwise not an element of this graph.
	 *
	 * @param edge the edge to remove
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean removeEdge(E edge) {
		System.out.println("removeEdge");

		if(edges.get(edge) == null)
		{
			return false;
		}
		else
		{
			ArrayList<E> updatedList = new ArrayList<>();
			for(V inEdges : edges.get(edge))
			{
				updatedList = verts.get(inEdges);
				updatedList.remove(edge);
				verts.put(inEdges, updatedList);
			}
			edges.remove(edge);
		}

		return true;
	}

	/**
	 * Removes vertex from this graph.
	 * As a side effect, removes any edges e incident to vertex if the
	 * removal of vertex would cause e to be incident to an illegal
	 * number of vertices.  (Thus, for example, incident hyperedges are not removed, but
	 * incident edges--which must be connected to a vertex at both endpoints--are removed.)
	 *
	 * <p>Fails under the following circumstances:
	 * <ul>
	 * <li/>vertex is not an element of this graph
	 * <li/>vertex is null
	 * </ul>
	 *
	 * @param vertex the vertex to remove
	 * @return true if the removal is successful, false otherwise
	 */
	public boolean removeVertex(V vertex) {
		System.out.println("removeVertex");

		if(verts.get(vertex) == null)
		{
			return false;
		}
		else
		{
			ArrayList<V> updatedList = new ArrayList<>();
			ArrayList<E> updatedListForEdges = new ArrayList<>();
			for(E inVerts : verts.get(vertex))
			{

				updatedList = edges.get(inVerts);
				updatedList.remove(vertex);
				edges.put(inVerts, updatedList);
				for(V inEdges : edges.get(inVerts))
				{
					updatedListForEdges = verts.get(inEdges);
					updatedListForEdges.remove(inVerts);
					verts.put(inEdges, updatedListForEdges);
				}
				edges.remove(inVerts);

			}
			verts.remove(vertex);
		}

		return true;


	}

	//--------------------------------------------------------
	// testing code goes here... edit this as much as you want!
	//--------------------------------------------------------

	/**
	 * This converts the super to String.
	 * @return the String of super.
	 */
	public String toString() {
		//you may edit this to make string representations of your
		//graph for testing
		return super.toString();
	}

	/**
	 * This is used for testing purposes.
	 * @param args not used.
	 */
	public static void main(String[] args) {
		//Some example testing code...
		/**
		 * A new class to test with.
		 */
		class Person extends ThreeTenGraphComponent {
			/**
			 * The constructor of the new class.
			 * @param id is to determine the difference of each Person class.
			 */
			public Person(int id) { super(id); }
		}
		/**
		 * Second testing class.
		 */
		class Cat extends ThreeTenGraphComponent {
			/**
			 * The constructor of the new class.
			 * @param id is to determine the difference of each Cat class.
			 */
			public Cat(int id) { super(id); }
		}
		/**
		 * Third testing class.
		 */
		class IntComponent extends ThreeTenGraphComponent {
			/**
			 * The constructor of the new class.
			 * @param id is to determine the difference of each IntComponent class.
			 */
			public IntComponent(int id) { super(id); }
		}

		//constructs a graph

		ThreeTenGraph<Person,Cat> graph1 = new ThreeTenGraph<>();
		for(int i = 0; i < 3; i++) {
			graph1.addVertex(new Person(i));
		}
		for(Person p : graph1.getVertices()) {
			graph1.addEdge(new Cat((int)(Math.random()*100)), p, p);
		}

		graph1.getNeighbors(new Person(1));

		if(graph1.getVertexCount() == 3 && graph1.getEdgeCount() == 3) {
			System.out.println("Yay 1");
		}

		//create a set of nodes and edges to test with
		IntComponent[] nodes = {
			new IntComponent(1),
			new IntComponent(26),
			new IntComponent(2),
			new IntComponent(25),
			new IntComponent(3),
			new IntComponent(24),
			new IntComponent(4),
			new IntComponent(23),
			new IntComponent(5),
			new IntComponent(22)
		};

		IntComponent[] edges = {
			new IntComponent(10000000),
			new IntComponent(4),
			new IntComponent(Integer.MAX_VALUE),
			new IntComponent(Integer.MIN_VALUE),
			new IntComponent(500),
			new IntComponent(600000)
		};

		//constructs a graph
		ThreeTenGraph<IntComponent,IntComponent> graph2 = new ThreeTenGraph<>();
		for(IntComponent n : nodes) {
			graph2.addVertex(n);
		}
		graph2.addEdge(edges[0],nodes[0],nodes[1]);
		graph2.addEdge(edges[1],nodes[2],nodes[2]);
		graph2.addEdge(edges[2],nodes[3],nodes[4]);
		graph2.addEdge(edges[3],nodes[6],nodes[7]);
		graph2.addEdge(edges[4],nodes[8],nodes[9]);
		graph2.addEdge(edges[5],nodes[9],nodes[0]);


		if(graph2.containsVertex(new IntComponent(1)) && graph2.containsEdge(new IntComponent(10000000))) {
			System.out.println("Yay 2");
		}

		//lot more testing here...
	}

	//********************************************************************************
	//   YOU MAY, BUT DON'T NEED TO EDIT THINGS IN THIS SECTION
	// This is a good place to look for "optimizing" your code to be faster.
	//********************************************************************************

	/**
	 * Returns true if this graph's vertex collection contains vertex.
	 * Equivalent to getVertices().contains(vertex).
	 * @param vertex the vertex whose presence is being queried
	 * @return true iff this graph contains a vertex vertex
	 */
	public boolean containsVertex(V vertex) {
		return getVertices().contains(vertex);
	}

	/**
	 * Returns true if this graph's edge collection contains edge.
	 * Equivalent to getEdges().contains(edge).
	 * @param edge the edge whose presence is being queried
	 * @return true iff this graph contains an edge edge
	 */
	public boolean containsEdge(E edge) {
		return getEdges().contains(edge);
	}

	/**
	 * Returns true if vertex and edge
	 * are incident to each other.
	 * Equivalent to getIncidentEdges(vertex).contains(edge) and to
	 * getIncidentVertices(edge).contains(vertex).
	 * @param vertex the vertex is touching the edge.
	 * @param edge the edge that is connected to the vertex.
	 * @return true if vertex and edge are incident to each other
	 */
	public boolean isIncident(V vertex, E edge) {
		return getIncidentEdges(vertex).contains(edge);
	}

	/**
	 * Returns true if v1 and v2 share an incident edge.
	 * Equivalent to getNeighbors(v1).contains(v2).
	 *
	 * @param v1 the first vertex to test
	 * @param v2 the second vertex to test
	 * @return true if v1 and v2 share an incident edge
	 */
	public boolean isNeighbor(V v1, V v2) {
		return getNeighbors(v1).contains(v2);
	}

	/**
	 * Returns true if v1 is a predecessor of v2 in this graph.
	 * Equivalent to v1.getPredecessors().contains(v2).
	 * @param v1 the first vertex to be queried
	 * @param v2 the second vertex to be queried
	 * @return true if v1 is a predecessor of v2, and false otherwise.
	 */
	public boolean isPredecessor(V v1, V v2) {
		return getPredecessors(v1).contains(v2);
	}

	/**
	 * Returns true if v1 is a successor of v2 in this graph.
	 * Equivalent to v1.getSuccessors().contains(v2).
	 * @param v1 the first vertex to be queried
	 * @param v2 the second vertex to be queried
	 * @return true if v1 is a successor of v2, and false otherwise.
	 */
	public boolean isSuccessor(V v1, V v2) {
		return getSuccessors(v1).contains(v2);
	}

	/**
	 * Returns the number of edges incident to vertex.
	 * Special cases of interest:
	 * <ul>
	 * <li/> Incident self-loops are counted once.
	 * <li> If there is only one edge that connects this vertex to
	 * each of its neighbors (and vice versa), then the value returned
	 * will also be equal to the number of neighbors that this vertex has
	 * (that is, the output of getNeighborCount).
	 * <li> If the graph is directed, then the value returned will be
	 * the sum of this vertex's indegree (the number of edges whose
	 * destination is this vertex) and its outdegree (the number
	 * of edges whose source is this vertex), minus the number of
	 * incident self-loops (to avoid double-counting).
	 * </ul>
	 *
	 * <p>Equivalent to getIncidentEdges(vertex).size().
	 *
	 * @param vertex the vertex whose degree is to be returned
	 * @return the degree of this node
	 * @see Hypergraph#getNeighborCount(Object)
	 */
	public int degree(V vertex) {
		return getIncidentEdges(vertex).size();
	}

	/**
	 * Returns the number of vertices that are adjacent to vertex
	 * (that is, the number of vertices that are incident to edges in vertex's
	 * incident edge set).
	 *
	 * <p>Equivalent to getNeighbors(vertex).size().
	 * @param vertex the vertex whose neighbor count is to be returned
	 * @return the number of neighboring vertices
	 */
	public int getNeighborCount(V vertex) {
		return getNeighbors(vertex).size();
	}

	/**
	 * Returns the number of incoming edges incident to vertex.
	 * Equivalent to getInEdges(vertex).size().
	 * @param vertex	the vertex whose indegree is to be calculated
	 * @return  the number of incoming edges incident to vertex
	 */
	public int inDegree(V vertex) {
		return getInEdges(vertex).size();
	}

	/**
	 * Returns the number of outgoing edges incident to vertex.
	 * Equivalent to getOutEdges(vertex).size().
	 * @param vertex	the vertex whose outdegree is to be calculated
	 * @return  the number of outgoing edges incident to vertex
	 */
	public int outDegree(V vertex) {
		return getOutEdges(vertex).size();
	}

	/**
	 * Returns the number of predecessors that vertex has in this graph.
	 * Equivalent to vertex.getPredecessors().size().
	 * @param vertex the vertex whose predecessor count is to be returned
	 * @return  the number of predecessors that vertex has in this graph
	 */
	public int getPredecessorCount(V vertex) {
		return getPredecessors(vertex).size();
	}

	/**
	 * Returns the number of successors that vertex has in this graph.
	 * Equivalent to vertex.getSuccessors().size().
	 * @param vertex the vertex whose successor count is to be returned
	 * @return  the number of successors that vertex has in this graph
	 */
	public int getSuccessorCount(V vertex) {
		return getSuccessors(vertex).size();
	}

	/**
	 * Returns the vertex at the other end of edge from vertex.
	 * (That is, returns the vertex incident to edge which is not vertex.)
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return the vertex at the other end of edge from vertex
	 */
	public V getOpposite(V vertex, E edge) {
		Pair<V> p = getEndpoints(edge);
		if(p.getFirst().equals(vertex)) {
			return p.getSecond();
		}
		else {
			return p.getFirst();
		}
	}

	/**
	 * Returns all edges that connects v1 to v2.
	 * If this edge is not uniquely
	 * defined (that is, if the graph contains more than one edge connecting
	 * v1 to v2), any of these edges
	 * may be returned.  findEdgeSet(v1, v2) may be
	 * used to return all such edges.
	 * Returns null if v1 is not connected to v2.
	 * <br/>Returns an empty collection if either v1 or v2 are not present in this graph.
	 *
	 * <p><b>Note</b>: for purposes of this method, v1 is only considered to be connected to
	 * v2 via a given <i>directed</i> edge d if
	 * v1 == d.getSource() && v2 == d.getDest() evaluates to true.
	 * (v1 and v2 are connected by an undirected edge u if
	 * u is incident to both v1 and v2.)
	 * @param v1 the set that is in V1
	 * @param v2 the set that is in v2
	 * @return  a collection containing all edges that connect v1 to v2, or null if either vertex is not present
	 * @see Hypergraph#findEdge(Object, Object)
	 */
	public Collection<E> findEdgeSet(V v1, V v2) {
		E edge = findEdge(v1, v2);
		if(edge == null) {
			return null;
		}

		ArrayList<E> ret = new ArrayList<>();
		ret.add(edge);
		return ret;

	}

	/**
	 * Returns true if vertex is the source of edge.
	 * Equivalent to getSource(edge).equals(vertex).
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return true iff vertex is the source of edge
	 */
	public boolean isSource(V vertex, E edge) {
		return getSource(edge).equals(vertex);
	}

	/**
	 * Returns true if vertex is the destination of edge.
	 * Equivalent to getDest(edge).equals(vertex).
	 * @param vertex the vertex to be queried
	 * @param edge the edge to be queried
	 * @return true iff vertex is the destination of edge
	 */
	public boolean isDest(V vertex, E edge) {
		return getDest(edge).equals(vertex);
	}

	/**
	 * Adds edge e to this graph such that it connects
	 * vertex v1 to v2.
	 * Equivalent to addEdge(e, new Pair(v1, v2)).
	 * If this graph does not contain v1, v2,
	 * or both, implementations may choose to either silently add
	 * the vertices to the graph or throw an IllegalArgumentException.
	 * If edgeType is not legal for this graph, this method will
	 * throw IllegalArgumentException.
	 * See Hypergraph.addEdge() for a listing of possible reasons
	 * for failure.
	 * @param e the edge to be added
	 * @param v1 the first vertex to be connected
	 * @param v2 the second vertex to be connected
	 * @param edgeType the type to be assigned to the edge
	 * @return true if the add is successful, false otherwise
	 * @see Hypergraph#addEdge(Object, Collection)
	 * @see #addEdge(Object, Object, Object)
	 */
	public boolean addEdge(E e, V v1, V v2, EdgeType edgeType) {
		//NOTE: Only undirected edges allowed

		if(edgeType == EdgeType.DIRECTED) {
			throw new IllegalArgumentException();
		}

		return addEdge(e, v1, v2);
	}

	/**
	 * Adds edge to this graph.
	 * Fails under the following circumstances:
	 * <ul>
	 * <li/>edge is already an element of the graph
	 * <li/>either edge or vertices is null
	 * <li/>vertices has the wrong number of vertices for the graph type
	 * <li/>vertices are already connected by another edge in this graph,
	 * and this graph does not accept parallel edges
	 * </ul>
	 *
	 * @param edge the edge that is being added
	 * @param vertices the vertex to indicate that the edge is being added.
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if edge or vertices is null, or if a different vertex set in this graph is
	already connected by edge,or if vertices are not a legal vertex set for edge
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(E edge, Collection<? extends V> vertices) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}

		V[] vs = (V[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1]);
	}

	/**
	 * Adds edge to this graph with type edge_type.
	 * Fails under the following circumstances:
	 * <ul>
	 * <li/>edge is already an element of the graph
	 * <li/>either edge or vertices is null
	 * <li/>vertices has the wrong number of vertices for the graph type
	 * <li/>vertices are already connected by another edge in this graph,
	 * and this graph does not accept parallel edges
	 * <li/>edge_type is not legal for this graph
	 * </ul>
	 *
	 * @param edge the edge that is being added.
	 * @param vertices the vertex that should be updated that the edge is being added.
	 * @param edgetype The type of edge.
	 * @return true if the add is successful, and false otherwise
	 * @throws IllegalArgumentException if edge or vertices is null, or if a different vertex set in this graph is
	already connected by edge, or if vertices are not a legal vertex set for edge
	 */
	@SuppressWarnings("unchecked")
	public boolean addEdge(E edge, Collection<? extends V> vertices, EdgeType edgetype) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}

		V[] vs = (V[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1], edgetype);
	}

	/**
	 * Returns the number of edges of type edge_type in this graph.
	 * @param edgetype the type of edge for which the count is to be returned
	 * @return the number of edges of type edge_type in this graph
	 */
	public int getEdgeCount(EdgeType edgetype) {
		if(edgetype == EdgeType.UNDIRECTED) {
			return getEdgeCount();
		}
		return 0;
	}

	/**
	 * Returns the collection of edges in this graph which are of type edge_type.
	 * @param edgetype the type of edges to be returned
	 * @return the collection of edges which are of type edge_type, or null if the graph does not accept edges of
	this type
	 * @see EdgeType
	 */
	public Collection<E> getEdges(EdgeType edgetype) {
		if(edgetype == EdgeType.UNDIRECTED) {
			return getEdges();
		}
		return null;
	}

	/**
	 * Returns the number of vertices that are incident to edge.
	 * For hyperedges, this can be any nonnegative integer; for edges this
	 * must be 2 (or 1 if self-loops are permitted).
	 *
	 * <p>Equivalent to getIncidentVertices(edge).size().
	 * @param edge the edge whose incident vertex count is to be returned
	 * @return the number of vertices that are incident to edge.
	 */
	public int getIncidentCount(E edge) {
		return getIncidentVertices(edge).size();
	}

	/**
	 * If directed_edge is a directed edge in this graph, returns the source;
	 * otherwise returns null.
	 * The source of a directed edge d is defined to be the vertex for which
	 * d is an outgoing edge.
	 * directed_edge is guaranteed to be a directed edge if
	 * its EdgeType is DIRECTED.
	 * @param directededge the directededge.
	 * @return  the source of directed_edge if it is a directed edge in this graph, or null otherwise
	 */
	public V getSource(E directededge) {
		return null;
	}

	/**
	 * If directed_edge is a directed edge in this graph, returns the destination;
	 * otherwise returns null.
	 * The destination of a directed edge d is defined to be the vertex
	 * incident to d for which
	 * d is an incoming edge.
	 * directed_edge is guaranteed to be a directed edge if
	 * its EdgeType is DIRECTED.
	 * @param directededge the de
	 * @return  the destination of directed_edge if it is a directed edge in this graph, or null otherwise
	 */
	public V getDest(E directededge) {
		return null;
	}

	/**
	 * Returns a Collection view of the predecessors of vertex
	 * in this graph.  A predecessor of vertex is defined as a vertex v
	 * which is connected to
	 * vertex by an edge e, where e is an outgoing edge of
	 * v and an incoming edge of vertex.
	 * @param vertex	the vertex whose predecessors are to be returned
	 * @return  a Collection view of the predecessors of vertex in this graph
	 */
	public Collection<V> getPredecessors(V vertex) {
		return getNeighbors(vertex);
	}

	/**
	 * Returns a Collection view of the successors of vertex
	 * in this graph.  A successor of vertex is defined as a vertex v
	 * which is connected to
	 * vertex by an edge e, where e is an incoming edge of
	 * v and an outgoing edge of vertex.
	 * @param vertex	the vertex whose predecessors are to be returned
	 * @return  a Collection view of the successors of vertex in this graph
	 */
	public Collection<V> getSuccessors(V vertex) {
		return getNeighbors(vertex);
	}

	/**
	 * Returns a Collection view of the incoming edges incident to vertex
	 * in this graph.
	 * @param vertex	the vertex whose incoming edges are to be returned
	 * @return  a Collection view of the incoming edges incident to vertex in this graph
	 */
	public Collection<E> getInEdges(V vertex) {
		return getIncidentEdges(vertex);
	}

	/**
	 * Returns a Collection view of the outgoing edges incident to vertex
	 * in this graph.
	 * @param vertex	the vertex whose outgoing edges are to be returned
	 * @return  a Collection view of the outgoing edges incident to vertex in this graph
	 */
	public Collection<E> getOutEdges(V vertex) {
		return getIncidentEdges(vertex);
	}

	/**
	 * Returns the endpoints of edge as a Pair.
	 * @param edge the edge whose endpoints are to be returned
	 * @return the endpoints (incident vertices) of edge
	 */
	@SuppressWarnings("unchecked")
	public Pair<V> getEndpoints(E edge) {
		Object[] ends = getIncidentVertices(edge).toArray();

		return new Pair<>((V)ends[0],(V)ends[1]);
	}
	
	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE (except to edit/fix the JavaDocs)
	//********************************************************************************
	
	/**
	 * Returns a {@code Factory} that creates an instance of this graph type.
	 * @param <V> the vertex type for the graph factory
	 * @param <E> the edge type for the graph factory
	 * @return the new graph.
	 */
	public static <V extends ThreeTenGraphComponent, E extends ThreeTenGraphComponent> Factory<Graph<V,E>> getFactory() { 
		return new Factory<Graph<V,E>> () {
			public Graph<V,E> create() {
				return new ThreeTenGraph<>();
			}
		};
	}
	/**
	 * Returns a {@code Factory} that creates an instance of this graph type.
	 * @param <V> the vertex type for the graph factory
	 * @param <E> the edge type for the graph factory
	 * @return the new graph
	 */
	public static <V extends ThreeTenGraphComponent, E extends ThreeTenGraphComponent> Factory<UndirectedGraph<V,E>> getUndirectedFactory() { 
		return new Factory<UndirectedGraph<V,E>> () {
			public UndirectedGraph<V,E> create() {
				return new ThreeTenGraph<>();
			}
		};
	}
	
	/**
	 * Returns the edge type of edge in this graph.
	 * @param edge the edge type that is wanted.
	 * @return the EdgeType of edge, or null if edge has no defined type
	 */
	public EdgeType getEdgeType(E edge) {
		return EdgeType.UNDIRECTED;
	}
	
	/**
	 * Returns the default edge type for this graph.
	 * 
	 * @return the default edge type for this graph
	 */
	public EdgeType getDefaultEdgeType() {
		return EdgeType.UNDIRECTED;
	}
}
