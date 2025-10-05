import networkx as nx
import random

def conectar_componentes(G):
    """Garante que o grafo seja conexo, conectando componentes."""
    componentes = list(nx.connected_components(G))
    while len(componentes) > 1:
        c1 = random.choice(list(componentes[0]))
        c2 = random.choice(list(componentes[1]))
        G.add_edge(c1, c2)
        componentes = list(nx.connected_components(G))
    return G

def ajustar_para_euleriano(G):
    """Ajusta o grafo para que todos os vértices tenham grau par."""
    graus = dict(G.degree())
    impares = [v for v, g in graus.items() if g % 2 == 1]
    while impares:
        u = impares.pop()
        v = impares.pop()
        if not G.has_edge(u, v):
            G.add_edge(u, v)
        else:
            G.remove_edge(u, v)
    return G

def ajustar_para_semi_euleriano(G):
    """Ajusta o grafo para que exatamente 2 vértices tenham grau ímpar."""
    graus = dict(G.degree())
    impares = [v for v, g in graus.items() if g % 2 == 1]
    while len(impares) > 2:
        u = impares.pop()
        v = impares.pop()
        if not G.has_edge(u, v):
            G.add_edge(u, v)
        else:
            G.remove_edge(u, v)
    return G

def gerar_grafo(tipo, n_vertices, n_arestas, nome_arquivo="grafo.txt"):
    G = nx.Graph()
    G.add_nodes_from(range(1, n_vertices+1))

    # adiciona arestas aleatórias até atingir o número desejado
    while G.number_of_edges() < n_arestas:
        u, v = random.sample(range(1, n_vertices+1), 2)
        if not G.has_edge(u, v):
            G.add_edge(u, v)

    # garante conectividade
    G = conectar_componentes(G)

    # ajusta conforme o tipo
    if tipo == "euleriano":
        G = ajustar_para_euleriano(G)
    elif tipo == "semi-euleriano":
        G = ajustar_para_semi_euleriano(G)
    # se for "nao-euleriano", não ajusta nada

    # salva no arquivo
    with open(nome_arquivo, "w") as f:
        f.write(f"{n_vertices} {G.number_of_edges()}\n")
        for u, v in G.edges():
            f.write(f"{u} {v}\n")

    return G


# ---------------- TESTES ----------------
testes = [
    (100, 200),
    (1000, 3000),
    (10000, 30000),
    (100000, 99999)
]

for n_vertices, n_arestas in testes:
    prefixo = f"g{n_vertices}"
    gerar_grafo("euleriano", n_vertices, n_arestas, f"{prefixo}_euleriano.txt")
    gerar_grafo("semi-euleriano", n_vertices, n_arestas, f"{prefixo}_semi.txt")
    gerar_grafo("nao-euleriano", n_vertices, n_arestas, f"{prefixo}_nao.txt")
    print(f"Geração concluída para {n_vertices} vértices e {n_arestas} arestas → arquivos {prefixo}_*.txt")
