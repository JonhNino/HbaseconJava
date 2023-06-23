package hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;

public class GettingStarted {
    public static void main(String[] args) {
        try {
            // Configuración de HBase
            Configuration conf = HBaseConfiguration.create();

            // Establecemos las propiedades
            conf.set("hbase.rootdir", "file:///tmp/hbase/hbase");
            conf.set("hbase.zookeeper.property.dataDir", "//tmp/hbase/zookeeper");
            conf.set("hbase.unsafe.stream.capability.enforce", "false");
            System.out.println("Se ha conectado a HBase");

            // Conexión al cliente HBase
            try (Connection connection = ConnectionFactory.createConnection(conf)) {
                TableName tableName = TableName.valueOf("cliente");
                try (Table table = connection.getTable(tableName)) {
                    System.out.println("Se ha conectado a la tabla 'cliente'");

                    // Definir el escaneo
                    Scan scan = new Scan();
                    try (ResultScanner scanner = table.getScanner(scan)) {
                        System.out.println("Comenzó a recorrer");

                        // Recorrer los resultados
                        Result res = scanner.next();
                        if (res != null) {
                            do {
                                System.out.println("Dentro");
                                System.out.println(res);
                                // Resto del código...
                            } while ((res = scanner.next()) != null);
                        } else {
                            System.out.println("No hay resultados para recorrer");
                        }
                    }
                }
            }

            System.out.println("Recurso Cerrado");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
