import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.hikaricp.internal.HikariCPConnectionProvider;

public class PoolSizeChecker {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jeuPU");
        SessionFactoryImplementor sfi = emf.unwrap(SessionFactoryImplementor.class);
        ConnectionProvider cp = sfi.getServiceRegistry().getService(ConnectionProvider.class);

        if (cp instanceof HikariCPConnectionProvider) {
            HikariDataSource hds = ((HikariCPConnectionProvider) cp).unwrap(HikariDataSource.class);
            System.out.println("Taille actuelle du pool de connexions : " + hds.getHikariPoolMXBean().getActiveConnections());
            System.out.println("Taille maximale du pool de connexions : " + hds.getMaximumPoolSize());
        }

        emf.close();
    }
}