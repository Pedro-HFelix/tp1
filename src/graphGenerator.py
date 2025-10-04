import networkx as nx
import random

def gerar_grafo(tipo, n_vertices, n_arestas, nome_arquivo="grafo.txt"):
    G = nx.Graph()
    G.add_nodes_from(range(1, n_vertices+1))

    # cria arestas aleatórias até atingir n_arestas
    while G.number_of_edges() < n_arestas:
        u, v = random.sample(range(1, n_vertices+1), 2)
        if not G.has_edge(u, v):
            G.add_edge(u, v)

    graus = dict(G.degree())
    impares = [v for v, g in graus.items() if g % 2 == 1]

    if tipo == "euleriano":
        # emparelhar todos os ímpares
        while len(impares) >= 2:
            u = impares.pop()
            v = impares.pop()
            if not G.has_edge(u, v):
                G.add_edge(u, v)
            else:
                # se já existe, conecta a outro vértice
                w = random.choice([x for x in range(1, n_vertices+1) if x not in (u,v)])
                G.add_edge(u, w)
            graus = dict(G.degree())
            impares = [v for v, g in graus.items() if g % 2 == 1]

    elif tipo == "semi-euleriano":
        # reduzir até sobrar 2 ímpares
        while len(impares) > 2:
            u = impares.pop()
            v = impares.pop()
            if not G.has_edge(u, v):
                G.add_edge(u, v)
            else:
                w = random.choice([x for x in range(1, n_vertices+1) if x not in (u,v)])
                G.add_edge(u, w)
            graus = dict(G.degree())
            impares = [v for v, g in graus.items() if g % 2 == 1]
        if len(impares) == 0:
            # força 2 ímpares
            u, v = random.sample(range(1, n_vertices+1), 2)
            if G.has_edge(u, v):
                G.remove_edge(u, v)
            else:
                G.add_edge(u, v)

    # salva no arquivo
    with open(nome_arquivo, "w") as f:
        f.write(f"{n_vertices} {G.number_of_edges()}\n")
        for u, v in G.edges():
            f.write(f"{u} {v}\n")


# lista de testes
testes = [
    (100, 200),
    (1000, 3000),
    (10000, 30000),
    (100000, 300000)
]

for n_vertices, n_arestas in testes:
    prefixo = f"g{n_vertices}"
    gerar_grafo("euleriano", n_vertices, n_arestas, f"{prefixo}_euleriano.txt")
    gerar_grafo("semi-euleriano", n_vertices, n_arestas, f"{prefixo}_semi.txt")
    gerar_grafo("nao-euleriano", n_vertices, n_arestas, f"{prefixo}_nao.txt")
    print(f"Geração concluída para {n_vertices} vértices e {n_arestas} arestas → arquivos {prefixo}_*.txt")
