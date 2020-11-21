<h1>Ex1 Project – weighted & Undirected Graph. - Welcome ! </h1>
<h3>This Project represent 3 Classes:</h3>
<p>Class WGraph_DS - implements weighted_graph.</p>

<p>Graph V – for Vertex , E – for Edges. -> each Edge contain a Weight Value</p>
<p>The Graph is using HashMap Data Structure for storing all the Vertexes of the Graph & Key for quick access to each Vertex.
Using also a Second HashMap for containing a list of Edges for each Vertex (+Weight).</p>

<p>The Graph is using HashMap Data Structure for storing all the Vertexes of the Graph & Key for quick access to each Vertex.
Using also a Second HashMap for containing a list of Edges for each Vertex (+Weight).</p>
<p>Info: -key values are Integer </p>
<p>Empty and Copy Constructors.</p>
<p>
For receiving/adding/remove a Vertex use getNode(key) -> node_info ; addNode(key) -> void ; removeNode(key) -> node_info.
For checking/receiving/remove an Edge use hasEdge(key1,key2) -> boolean value ; getEdge(key1,key2) -> double value ; removeEdge(key1,key2) -> void.
Collection<node_info> - getV() -> Collection of Vertices of the Graph ; getV(node_id) -> Collection of Vertices that hasEdge with node_id.
nodeSize() -> Integer ; edgeSize() -> Integer ; getMC() -> Integer - Counter for Graph Modifications 
<p>
	[private] Class NodeData - implements node_info -> Inner Class of WGraph_DS.

	Each Node is representing a Vertex of the Graph. 
	Contains these elements:
	Key Integer for identification.
	Tag Real_Number for using in Graph Algorithms.
	String Value - meta_data.
	Info:
	Receiving a Key getKey() , set/get.Tag((double)set) , set/get.Info("set"). 
<p>Class WGraph_Algo - implements weighted_graph_algorithms.</p>

Checking the Graph above if it is Connected – (Every Vertex is reachable from Every Vertex). <br>
Returning the Shortest path from Vertex - A to Vertex – B, (A,B\ \in\ V) <br>

<p>info:</p>
<p>
For Applying any method on a graph first use init(weighted_graph) to initiate the Graph of the Class. <br>
IF you are not sure which Graph you working on use getGraph() -> weighted_graph. <br>
Also save\load weighted_graph is possible save(File Name) ; load(File Name) -> Both Methods return boolean value for success or not. <br>
For checking if the Graph is connected apply isConnected() -> boolean value <br>
isConnected() - Use BFS - Breath-First-Search Algorithm -> Granting each Vertex a Tag Value. <br>
Explanation: each Vertex receive a Value that represent the number of Vertices from the Source Vertex. <br>
There are 2 Methods for receiving the Shortest Path - <br>
<div>
shortestPathDist(src - Key, dest - Key) - return double value - represent the shortest distance from src to dest (Weight of Edges) <br>
shortestPath(src - Key, dest - Key) - return List - Vertex(src) -> Vertex(1) -> V -> V -> ... -> Vertex(dest) <br>
Both Shortest Path Methods - Uses Dijkstra Algorithm -> Granting each Vertex a Tag Value (EdgeWeight Sum Form Source)-{Min_Sum} <br>
</div>



