import networkx as nx
import random

def gerar_grafo(tipo, n_vertices, n_arestas, nome_arquivo="grafo.txt"):
    G = nx.Graph()
    G.add_nodes_from(range(1, n_vertices+1))

    while G.number_of_edges() < n_arestas:
        u, v = random.sample(range(1, n_vertices+1), 2)
        if not G.has_edge(u, v):
            G.add_edge(u, v)

    graus = dict(G.degree())
    impares = [v for v, g in graus.items() if g % 2 == 1]

    if tipo == "euleriano":
        # precisa de 0 ímpares → se houver, ajusta
        while len(impares) > 0:
            u, v = random.sample(impares, 2)
            if not G.has_edge(u, v):
                G.add_edge(u, v)
            else:
                G.remove_edge(u, v)
            graus = dict(G.degree())
            impares = [v for v, g in graus.items() if g % 2 == 1]

    elif tipo == "semi-euleriano":
        # precisa de 2 ímpares
        while len(impares) != 2:
            u, v = random.sample(range(1, n_vertices+1), 2)
            if G.has_edge(u, v):
                G.remove_edge(u, v)
            else:
                G.add_edge(u, v)
            graus = dict(G.degree())
            impares = [v for v, g in graus.items() if g % 2 == 1]

    # se for "nao-euleriano", não precisa ajustar nada

    # salva no arquivo
    with open(nome_arquivo, "w") as f:
        f.write(f"{n_vertices} {G.number_of_edges()}\n")
        for u, v in G.edges():
            f.write(f"{u} {v}\n")

gerar_grafo("euleriano", 11, 15, "grafo_euleriano.txt")
gerar_grafo("semi-euleriano", 11, 15, "grafo_semi.txt")
gerar_grafo("nao-euleriano", 11, 15, "grafo_nao.txt")
