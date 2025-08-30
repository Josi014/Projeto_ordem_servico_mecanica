/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;
import javax.persistence.*;
import model.*;
import model.OrdemServico;
import view.PrincipalJF.SegurancaUtil;

/**
 *
 * @author Josieli
 */
public class PersistenciaJPA implements InterfaceBD {

    private static final EntityManagerFactory emf
            = Persistence.createEntityManagerFactory("pu-bbylinpux-ordem-servico-mecanica");

    private EntityManager entity;

    public PersistenciaJPA() {
        this.entity = emf.createEntityManager();
    }

    @Override
    public Boolean conexaoAberta() {
        return entity != null && entity.isOpen();
    }

    @Override
    public void fecharConexao() {
        if (entity != null && entity.isOpen()) {
            entity.close();
        }
    }

    @Override
    public Object find(Class c, Object id) throws Exception {
        return getEntityManager().find(c, id);
    }

    @Override
    public void persist(Object o) throws Exception {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(o);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    @Override
    public void remover(Object o) throws Exception {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (!em.contains(o)) {
                o = em.merge(o);
            }
            em.remove(o);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            Logger.getLogger(PersistenciaJPA.class.getName())
                    .log(Level.SEVERE, "Erro ao remover: " + o.getClass().getSimpleName(), e);
            throw e;
        }
    }

    public EntityManager getEntityManager() {
        if (entity == null || !entity.isOpen()) {
            entity = emf.createEntityManager();
        }
        return entity;
    }

    public List<Cliente> getClientes() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(PersistenciaJPA.class.getName())
                    .log(Level.SEVERE, "Erro ao buscar clientes", e);
            return new ArrayList<>();
        }
    }

    public Usuario autenticarCliente(String login, String senhaDigitada) {
        EntityManager em = getEntityManager();
        try {
            Usuario usuarioEncontrado = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.usuario = :login", Usuario.class)
                    .setParameter("login", login)
                    .getSingleResult();

            if (SegurancaUtil.verificarSenha(senhaDigitada, usuarioEncontrado.getSenha())) {
                return usuarioEncontrado;
            } else {
                return null;
            }
        } catch (NoResultException e) {
            return null;
        }
    }

    public Cliente buscarClientePorNome(String nome) {
        EntityManager em = getEntityManager();
        try {
            return em.createQuery(
                    "SELECT c FROM Cliente c WHERE LOWER(c.nome) = :nome", Cliente.class)
                    .setParameter("nome", nome.toLowerCase())
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public Cliente buscarClientePorId(Long id) {
        return getEntityManager().find(Cliente.class, id);
    }

    public List<Cliente> buscarClientesPorNomeParcial(String termo) {
        EntityManager em = getEntityManager();
        TypedQuery<Cliente> query = em.createQuery(
                "SELECT c FROM Cliente c WHERE LOWER(c.nome) LIKE :termo", Cliente.class);

        query.setParameter("termo", termo.toLowerCase() + "%");
        query.setMaxResults(10);

        return query.getResultList();
    }

    public void salvarOuAtualizarCliente(Cliente cliente) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (cliente.getId() == null) {
                em.persist(cliente);
            } else {
                em.merge(cliente);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    public void removerCliente(Cliente cliente) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            cliente = em.find(Cliente.class, cliente.getId());
            List<OrdemServico> ordens = em.createQuery(
                    "SELECT o FROM OrdemServico o WHERE o.cliente = :cliente", OrdemServico.class)
                    .setParameter("cliente", cliente)
                    .getResultList();
            for (OrdemServico os : ordens) {
                os.setCliente(null);
                em.merge(os);
            }
            em.remove(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            Logger.getLogger(PersistenciaJPA.class.getName())
                    .log(Level.SEVERE, "Erro ao remover cliente", e);
            throw e;
        }
    }

    public List<OrdemServico> buscarOrdensPorCliente(Cliente cliente) {
        return getEntityManager().createQuery(
                "SELECT o FROM OrdemServico o WHERE o.cliente = :cliente", OrdemServico.class)
                .setParameter("cliente", cliente)
                .getResultList();
    }

    public List<OrdemServico> getOrdensDeServico() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<OrdemServico> query = em.createQuery(
                    "SELECT o FROM OrdemServico o LEFT JOIN FETCH o.cliente", OrdemServico.class);
            return query.getResultList();
        } catch (Exception e) {
            Logger.getLogger(PersistenciaJPA.class.getName())
                    .log(Level.SEVERE, "Erro ao buscar ordens de serviço", e);
            return new ArrayList<>();
        }
    }

    public OrdemServico buscarOrdemServicoPorId(Long id) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<OrdemServico> query = em.createQuery(
                    "SELECT o FROM OrdemServico o LEFT JOIN FETCH o.cliente WHERE o.id = :id", OrdemServico.class);
            return query.setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public void atualizarOrdemServico(OrdemServico os) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(os);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        }
    }

    public Long buscarProximoIdOrdemServico() {
        EntityManager em = emf.createEntityManager();
        try {
            String seq = (String) em.createNativeQuery(
                    "SELECT pg_get_serial_sequence('ordem_servico','ordem_servico_id')")
                    .getSingleResult();
            Number next = (Number) em.createNativeQuery("SELECT last_value + 1 FROM " + seq)
                    .getSingleResult();
            return next.longValue();
        } catch (Exception e) {
            return 1L;
        } finally {
            em.close();
        }
    }

    public void salvarOuAtualizarOrdemServico(OrdemServico ordem) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            if (ordem.getId() == null) {
                em.persist(ordem);
            } else {
                em.merge(ordem);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public static void fecharFactory() {
        if (emf.isOpen()) {
            emf.close();
        }
    }
}
