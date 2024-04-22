
// Eray Zor, 941157
// Levent Avgören, 941761
import graph.*;
import java.util.*;


public class PRIM {

        //Vertices = Knoten
        //Edge = Kanten
        //Vertex = id

        //getNeighbours- Von dem Knoten aus die Nachbarn
        //getVertex - Die Knoten selbst
        //getEdges() - Bekommen die Kanten
        //getIncidentEdges() - Gewicht der Kanten + die Nachbarn von dem Punkt
        //getVertices() - Die Knoten bekommen wir als Vertex
        //getNumberVertices() - Anzahl der Knoten
        //graph.getIncidentEdges(i).size() - gibt die nachbarn als zahl zurück
        Graph<Vertex, Edge<Vertex>> graph;
    public PRIM(String dataPath){
        //Packen unseren File in einen Graphen
        graph = GraphRead.FileToGraph(dataPath, false, false, true);

    }



    public int MSTprime(){

            //Initialisieren zwei Hashmaps
            Map<Integer, Vertex> pred = new HashMap<>();
            Map<Integer, Integer> minW = new HashMap<>();

            //Initialisieren zwei Listen
            //Arraylist nutzen wir, um die Knoten zu speichern, die wir noch nicht besucht haben
            //LinkedList speichert die Nachbarn zum jeweiligen Knoten.
            List<Vertex> verticesVisited = new LinkedList<>(graph.getVertices());
            List<Edge<Vertex>> alreadyVisitedEdges = new LinkedList<>();

            //Priority-Queue wird initialisiert
            PriorityQueue<Edge<Vertex>> pq = new PriorityQueue<>();

            //Packen unsere Vertices mit MAX_VALUE zusammen → Aus der Vorlesung
            for (Vertex vert : verticesVisited) {

                minW.put(vert.getId(), Integer.MAX_VALUE);

            }

            //MST Gewicht
            int minimumWeight = 0;


            int minV = Integer.MAX_VALUE;


            //Startknoten ermitteln
            for (int i = 0; i < graph.getNumberVertices(); i++) {
                //getNumberVertices() - Anzahl der Knoten
                Vertex v = graph.getVertex(i);
                if (v != null && v.getId() < minV) {
                    minV = v.getId();
                }
            }

            //packen beim Startknoten als Gewicht 0, da wir noch am Anfang sind.
            //pred ist null, da wir noch keinen Predecessor haben,
            minW.put(minV, 0);
            pred.put(minV, null);

            //Solange unsere Knoten Liste nicht leer ist, gehen wir die Liste durch
            while (!verticesVisited.isEmpty()) {

                //Holen uns den kleinsten Knoten raus
                //und löschen ihn direkt von der Liste
                Vertex lowerVertex = verticesVisited.get(0);
                verticesVisited.remove(lowerVertex);

                //Packen die Nachbarn in unsere Priority-Queue
                //getIncidentEdges - Kanten mit Gewicht
                for (Edge<Vertex> vertexEdge : graph.getIncidentEdges(lowerVertex.getId())) {

                    if (!alreadyVisitedEdges.contains(vertexEdge)) {

                        pq.offer(vertexEdge);

                    }

                }

                //Fügen die Kante mit dem geringsten Gewicht hinzu
                Edge<Vertex> nextEdge = pq.poll();

                if (nextEdge != null) {
                    //Gehen die Nachbarn vom Knoten durch
                    for (Vertex neighbour : graph.getNeighbours(lowerVertex.getId())) {
                        //Schauen nach, ob wir den Nachbar schon besucht haben und vergleichen die Gewichte.
                        if (verticesVisited.contains(neighbour) && nextEdge.getWeight() < minW.get(neighbour.getId())) {

                            alreadyVisitedEdges.add(nextEdge);
                            pred.put(neighbour.getId(), lowerVertex);
                            minW.put(neighbour.getId(), nextEdge.getWeight());
                        }
                    }
                }

                pq.clear();
            }


            //Gewicht addieren
            Set<Integer> keysFromPred = pred.keySet();
            for (int theKeys : keysFromPred) {
                Vertex endV = pred.get(theKeys);

                if (endV == null) {
                    continue;
                }

                for (Edge<Vertex> ed : graph.getIncidentEdges(theKeys)) {

                    if (ed.getVertexA().getId() == endV.getId() || ed.getVertexB().getId() == endV.getId()) {

                        minimumWeight += ed.getWeight();

                    }

                }
            }


           return minimumWeight;

        }

}



