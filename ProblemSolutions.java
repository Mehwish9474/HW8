/******************************************************************
 *
 *   Mehwish Tabassum / 272 - 002
 *
 *   This java file contains the problem solutions of canFinish and
 *   numGroups methods.
 *
 ********************************************************************/

import java.util.*;

class ProblemSolutions {

    /**
     * Method canFinish
     *
     * You are building a course curriculum along with required intermediate
     * exams certifications that must be taken by programmers in order to obtain
     * a new certification called 'master programmer'. In doing so, you are placing
     * prerequisites on intermediate exam certifications that must be taken first.
     * You are allowing the flexibility of taking the exams in any order as long as
     * any exam prerequisites are satisfied.
     *
     * Unfortunately, in the past, your predecessors have accidentally published
     * curriculums and exam schedules that were not possible to complete due to cycles
     * in prerequisites. You want to avoid this embarrassment by making sure you define
     * a curriculum and exam schedule that can be completed.
     *
     * You goal is to ensure that any student pursuing the certificate of 'master
     * programmer', can complete 'n' certification exams, each being specific to a
     * topic. Some exams have prerequisites of needing to take and pass earlier
     * certificate exams. You do not want to force any order of taking the exams, but
     * you want to make sure that at least one order is possible.
     *
     * This method will save your embarrassment by returning true or false if
     * there is at least one order that can taken of exams.
     *
     * You wrote this method, and in doing so, you represent these 'n' exams as
     * nodes in a graph, numbered from 0 to n-1. And you represent the prerequisite
     * between taking exams as directed edges between two nodes which represent
     * those two exams.
     *
     * Your method expects a 2-dimensional array of exam prerequisites, where
     * prerequisites[i] = [ai, bi] indicating that you must take exam 'bi' first
     * if you want to take exam 'ai'. For example, the pair [0, 1], indicates that
     * to take exam certification '0', you have to first have the certification for
     * exam '1'.
     *
     * The method will return true if you can finish all certification exams.
     * Otherwise, return false (e.g., meaning it is a cyclic or cycle graph).
     *
     *     Example 1:
     *     Input: numExams = 2, prerequisites = [[1,0]]
     *     Output: true
     *     Explanation: There are a total of 2 exams to take.
     *     To take exam 1 you should have completed the
     *     certification of exam 0. So, it is possible (no
     *     cyclic or cycle graph of prereqs).
     *
     *
     *     Example 2:
     *     Input: numExams = 2, prerequisites = [[1,0],[0,1]]
     *     Output: false
     *     Explanation: There are a total of 2 exams to take.
     *     To take exam 1 you should have completed the
     *     certification of exam 0, and to take exams 0 you
     *     should also have completed the certification of exam
     *     1. So, it is impossible (it is a cycle graph).
     *
     * @param numExams          - number of exams, which will produce a graph of n nodes
     * @param prerequisites     - 2-dim array of directed edges.
     * @return boolean          - True if all exams can be taken, else false.
     */

     public boolean canFinish(int numExams, int[][] prerequisites) {
        @SuppressWarnings("unchecked")
        ArrayList<Integer>[] adj = new ArrayList[numExams];
        int[] inDegree = new int[numExams];
    
        // Initialize adjacency list and in-degree array
        for (int i = 0; i < numExams; i++) {
            adj[i] = new ArrayList<>();
        }
    
        for (int[] edge : prerequisites) {
            adj[edge[1]].add(edge[0]);
            inDegree[edge[0]]++;
        }
    
        // Use a queue for topological sorting (Kahn's Algorithm)
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numExams; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }
    
        int processedNodes = 0;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            processedNodes++;
    
            for (int neighbor : adj[current]) {
                inDegree[neighbor]--;
                if (inDegree[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
        }
    
        // Debug Output
        System.out.println("Processed nodes: " + processedNodes + ", Total nodes: " + numExams);
    
        // Return true if all nodes were processed (no cycle)
        return processedNodes == numExams;
    }
    /**
     * Method getAdjList
     *
     * Building an Adjacency List for the directed graph based on number of nodes
     * and passed in directed edges.
     *
     * @param numNodes      - number of nodes in graph (labeled 0 through n-1) for n nodes
     * @param edges         - 2-dim array of directed edges
     * @return ArrayList<Integer>[]  - An adjacency list representing the provided graph.
     */



    /*
     * Assignment Graphing - Number of groups.
     *
     * There are n people. Some of them are connected
     * as friends forming a group. If person 'a' is
     * connected to person 'b', and person 'b' is
     * connected to person 'c', they form a connected
     * group.
     *
     * Not all groups are interconnected, meaning there
     * can be 1 or more groups depending on how people
     * are connected.
     *
     * This example can be viewed as a graph problem,
     * where people are represented as nodes, and
     * edges between them represent people being
     * connected. In this problem, we are representing
     * this graph externally as an non-directed
     * Adjacency Matrix. And the graph itself may not
     * be fully connected, it can have 1 or more
     * non-connected compoents (subgraphs).
     *
     * Example 1:
     *   Input :
         AdjMatrix = [[0,1,0], [1,0,0], [0,0,0]]
     *   Output: 2
     *   Explanation: The Adjacency Matrix defines an
     *   undirected graph of 3 nodes (indexed 0 to 2).
     *   Where nodes 0 and 1 aee connected, and node 2
     *   is NOT connected. This forms two groups of
     *   nodes.
     *
     * Example 2:
     *   Input : AdjMatrix = [ [0,0,0], [0,0,0], [0,0,0]]
     *   Output: 3
     *   Explanation: The Adjacency Matrix defines an
     *   undirected graph of 3 nodes (indexed 0 to 2).
     *   There are no connected nodes, hence forming
     *   three groups.
     *
     * Example 3:
     *   Input : AdjMatrix = [[0,1,0], [1,0,0], [0,1,0]]
     *   Output: 1
     *   Explanation, The adjacency Matrix defined an
     *   undirected graph of 3 nodes (index 0 to 2).
     *   All three nodes are connected by at least one
     *   edge. So they form on large group.
     */

     public int numGroups(int[][] adjMatrix) {
        if (adjMatrix == null || adjMatrix.length == 0) {
            return 0; // Handle empty matrix
        }
    
        int n = adjMatrix.length;
        boolean[] visited = new boolean[n];
        int groupCount = 0;
    
        // Ensure symmetry for undirected graph
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (adjMatrix[i][j] != adjMatrix[j][i]) {
                    adjMatrix[i][j] = adjMatrix[j][i] = 1;
                }
            }
        }
    
        // Count connected groups using DFS
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                groupCount++;
                dfsTraverse(adjMatrix, visited, i);
            }
        }
    
        return groupCount;
    }
    
    private void dfsTraverse(int[][] adjMatrix, boolean[] visited, int node) {
        visited[node] = true;
    
        for (int neighbor = 0; neighbor < adjMatrix.length; neighbor++) {
            if (adjMatrix[node][neighbor] == 1 && !visited[neighbor]) {
                dfsTraverse(adjMatrix, visited, neighbor);
            }
        }
    }                                             
}
