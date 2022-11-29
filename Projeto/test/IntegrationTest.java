import model.client.Client;
import model.server.Server;
import model.server.ServerObserver;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
    //Campos ServerObserver
    private static String notificacaoOnlineServerEsperado;
    private static String notificacaoOnlineServerObtido;
    private static String notificacaoOfflineServerEsperado;
    private static String notificacaoOfflineServerObtido;
    private static String notificacaoQuitServerEsperado;
    private static String notificacaoQuitServerObtido;

    //Capos ClientObserver Client1
    private Client client1;
    private String notificacaoOnlineClient1Esperado;
    private String notificacaoOnlineClient1Obtido;
    private String mensagemRecebidaClient1Esperado;
    private String mensagemRecebidaClient1Obtido;
    private String listaDeHostsClient1Esperado;
    private String listaDeHostsClient1Obtido;
    private String notificacaoOfflineClient1Esperado;
    private String notificacaoOfflineClient1Obtido;
    private String notificacaoQuitClient1Esperado;
    private String notificacaoQuitClient1Obtido;

    //Capos ClientObserver Client2
    private Client client2;
    private String mensagemRecebidaClient2Esperado;
    private String mensagemRecebidaClient2Obtido;

    //Capos ClientObserver Client3
    private Client client3;
    private String listaDeHostsClient3Esperado;
    private String listaDeHostsClient3Obtido;

    private static int port = 8081;

    @BeforeClass
    public static void setUpClass() throws IOException {
        Server server = new Server(new ServerObserver() {
            @Override
            public void notifyClose() {}
    
            @Override
            public void notifyError() {}
    
            @Override
            public void notifyOffline(String name) {
                notificacaoOfflineServerObtido = name + " desconectou";
            }
    
            @Override
            public void notifyOnline(String name) {
                notificacaoOnlineServerObtido = name + " conectou";
            }
    
            @Override
            public void notifyQuit(String name) {
                notificacaoQuitServerObtido = name + " saiu do grupo";
            }
    
            @Override
            public void notifyWaiting() {}
        }, port);
    }
    
    @Before
    public void setUpMethod() {
        notificacaoOnlineServerObtido = "";
        notificacaoOfflineServerObtido = "";
        notificacaoQuitServerObtido = "";
    }
    
    @After
    public void after() {
        try {
            if(client1 != null) {
                client1.quit();
            }
            if(client2 != null) {
                client2.quit();
            }
            if(client3 != null) {
                client3.quit();
            }
        } catch(IOException ignored) {
        
        }
    }

    @Test
    public void testConectarBemSucedido() {
        notificacaoOnlineClient1Esperado = "Teste1 conectou";
        notificacaoOnlineServerEsperado = "Teste1 conectou";
        notificacaoOnlineClient1Obtido = "";
        try {
            client1 = new Client(new AbstractClientObserver() {
                @Override
                public void notifyOnline(String name) {
                    notificacaoOnlineClient1Obtido = name + " conectou";
                }
            }, "127.0.0.1", port, "Teste1");
            while(notificacaoOnlineClient1Obtido.equals("") || notificacaoOnlineServerObtido.equals("")) {
                Thread.onSpinWait();
            }
        } catch(IOException ignored) {
        
        }
        assertEquals(notificacaoOnlineClient1Esperado, notificacaoOnlineClient1Obtido);
        assertEquals(notificacaoOnlineServerEsperado, notificacaoOnlineServerObtido);
    }

    @Test
    public void testConectarMalSucedido() {
        notificacaoOnlineClient1Esperado = "";
        notificacaoOnlineServerEsperado = "";
        notificacaoOnlineClient1Obtido = "";
        try {
            client1 = new Client(new AbstractClientObserver() {
                @Override
                public void notifyOnline(String name) {
                    notificacaoOnlineClient1Obtido = name + " conectou";
                }
            }, "127.0.0.1", 1234, "Teste1");
            while(notificacaoOnlineClient1Obtido.equals("") || notificacaoOnlineServerObtido.equals("")) {
                Thread.onSpinWait();
            }
        } catch(IOException ignored) {

        }
        assertEquals(notificacaoOnlineClient1Esperado, notificacaoOnlineClient1Obtido);
        assertEquals(notificacaoOnlineServerEsperado, notificacaoOnlineServerObtido);
    }

    @Test
    public void testEnvioMensagemBemSucedidoCaso1() {
        String message = "Oi, Teste2, como voce esta?";
        mensagemRecebidaClient1Esperado = "Voce: Oi, Teste2, como voce esta?";
        mensagemRecebidaClient2Esperado = "Teste1: Oi, Teste2, como voce esta?";
        mensagemRecebidaClient1Obtido = "";
        mensagemRecebidaClient2Obtido = "";
        try {
            client1 = new Client(new AbstractClientObserver() {
                @Override
                public void showMessage(String who, String message) {
                    mensagemRecebidaClient1Obtido = who + ": " + message;
                }
            }, "127.0.0.1", port, "Teste1");
            client2 = new Client(new AbstractClientObserver() {
                @Override
                public void showMessage(String who, String message) {
                    mensagemRecebidaClient2Obtido = who + ": " + message;
                }
            }, "127.0.0.1", port, "Teste2");
            client1.sendMessage(message);
            while(mensagemRecebidaClient1Obtido.equals("") || mensagemRecebidaClient2Obtido.equals("")) {
                Thread.onSpinWait();
            }
        } catch(IOException ignored) {

        }
        assertEquals(mensagemRecebidaClient1Esperado, mensagemRecebidaClient1Obtido);
        assertEquals(mensagemRecebidaClient2Esperado, mensagemRecebidaClient2Obtido);
    }

    @Test
    public void testEnvioMensagemBemSucedidoCaso2() {
        String message = "Oi, Teste1, estou bem e voce?";
        mensagemRecebidaClient1Esperado = "Teste2: Oi, Teste1, estou bem e voce?";
        mensagemRecebidaClient2Esperado = "Voce: Oi, Teste1, estou bem e voce?";
        mensagemRecebidaClient1Obtido = "";
        mensagemRecebidaClient2Obtido = "";
        try {
            client1 = new Client(new AbstractClientObserver() {
                @Override
                public void showMessage(String who, String message) {
                    mensagemRecebidaClient1Obtido = who + ": " + message;
                }
            }, "127.0.0.1", port, "Teste1");
            client2 = new Client(new AbstractClientObserver() {
                @Override
                public void showMessage(String who, String message) {
                    mensagemRecebidaClient2Obtido = who + ": " + message;
                }
            }, "127.0.0.1", port, "Teste2");
            client2.sendMessage(message);
            while(mensagemRecebidaClient1Obtido.equals("") || mensagemRecebidaClient2Obtido.equals("")) {
                Thread.onSpinWait();
            }
        } catch(IOException ignored) {

        }
        assertEquals(mensagemRecebidaClient1Esperado, mensagemRecebidaClient1Obtido);
        assertEquals(mensagemRecebidaClient2Esperado, mensagemRecebidaClient2Obtido);
    }

    @Test
    public void testListHostsBemSucedidoCaso1() {
        listaDeHostsClient1Esperado = "Teste2;OFFLINE;";
        listaDeHostsClient1Obtido = "";
        try {
            client2 = new Client(new AbstractClientObserver() {}, "127.0.0.1", port, "Teste2");
            client2.disconnect();
            client1 = new Client(new AbstractClientObserver() {
                @Override
                public void listHosts(String[] hosts) {
                    String aux = "";
                    for (String string : hosts) {
                        aux += string+";";
                    }
                    listaDeHostsClient1Obtido = aux;
                }
            }, "127.0.0.1", port, "Teste1");
            while(listaDeHostsClient1Obtido.equals("")) {
                Thread.onSpinWait();
            }
        } catch(IOException ignored) {

        }
        assertEquals(listaDeHostsClient1Esperado, listaDeHostsClient1Obtido);
    }

    @Test
    public void testListHostsBemSucedidoCaso2() {
        listaDeHostsClient1Esperado = "Teste2;ONLINE;Teste3;OFFLINE;";
        listaDeHostsClient1Obtido = "";
        try {
            client2 = new Client(new AbstractClientObserver() {}, "127.0.0.1", port, "Teste2");
            client3 = new Client(new AbstractClientObserver() {}, "127.0.0.1", port, "Teste3");
            client3.disconnect();
            client1 = new Client(new AbstractClientObserver() {
                @Override
                public void listHosts(String[] hosts) {
                    String aux = "";
                    for (String string : hosts) {
                        aux += string+";";
                    }
                    listaDeHostsClient1Obtido = aux;
                }
            }, "127.0.0.1", port, "Teste1");
            while(listaDeHostsClient1Obtido.equals("")) {
                Thread.onSpinWait();
            }
        } catch(IOException ignored) {

        }
        assertEquals(listaDeHostsClient1Esperado, listaDeHostsClient1Obtido);
    }

    @Test
    public void testDesconectarBemSucedidoCaso1() {
        notificacaoOfflineServerEsperado = "Teste1 desconectou";
        try {
            client1 = new Client(new AbstractClientObserver() {}, "127.0.0.1", port, "Teste1");
            client1.disconnect();
            while(notificacaoOfflineServerObtido.equals("")) {
                Thread.onSpinWait();
            }
            
        } catch(IOException ignored) {

        }
        assertEquals(notificacaoOfflineServerEsperado, notificacaoOfflineServerObtido);
    }

    @Test
    public void testDesconectarBemSucedidoCaso2() {
        notificacaoOfflineClient1Esperado = "Teste2 desconectou";
        notificacaoOfflineServerEsperado = "Teste2 desconectou";
        notificacaoOfflineClient1Obtido = "";
        try {
            client1 = new Client(new AbstractClientObserver() {
                @Override
                public void notifyOffline(String name) {
                    notificacaoOfflineClient1Obtido = name + " desconectou";
                }
            }, "127.0.0.1", port, "Teste1");
            client2 = new Client(new AbstractClientObserver() {}, "127.0.0.1", port, "Teste2");
            client2.disconnect();
            while(notificacaoOfflineClient1Obtido.equals("") || notificacaoOfflineServerObtido.equals("")) {
                Thread.onSpinWait();
            }
        
        } catch(IOException ignored) {
        
        }
        assertEquals(notificacaoOfflineClient1Esperado, notificacaoOfflineClient1Obtido);
        assertEquals(notificacaoOfflineServerEsperado, notificacaoOfflineServerObtido);
    }

    @Test
    public void testSairBemSucedidoCaso1() {
        notificacaoQuitServerEsperado = "Teste1 saiu do grupo";
        try {
            client1 = new Client(new AbstractClientObserver() {}, "127.0.0.1", port, "Teste1");
            client1.quit();
            while(notificacaoQuitServerObtido.equals("")) {
                Thread.onSpinWait();
            }
        } catch(IOException ignored) {

        }
        assertEquals(notificacaoQuitServerEsperado, notificacaoQuitServerObtido);
    }

    @Test
    public void testSairBemSucedidoCaso2() {
        notificacaoQuitServerEsperado = "Teste2 saiu do grupo";
        notificacaoQuitClient1Esperado = "Teste2 saiu do grupo";
        notificacaoQuitClient1Obtido = "";
        listaDeHostsClient1Esperado = "Teste2;ONLINE;";
        listaDeHostsClient1Obtido = "";
        listaDeHostsClient3Esperado = "Teste1;ONLINE;";
        listaDeHostsClient3Obtido = "";
        try {
            client2 = new Client(new AbstractClientObserver() {}, "127.0.0.1", port, "Teste2");
            client1 = new Client(new AbstractClientObserver() {
                @Override
                public void listHosts(String[] hosts) {
                    String aux = "";
                    for (String string : hosts) {
                        aux += string+";";
                    }
                    listaDeHostsClient1Obtido = aux;
                }
    
                @Override
                public void notifyQuit(String name) {
                    notificacaoQuitClient1Obtido = name + " saiu do grupo";
                }
            }, "127.0.0.1", port, "Teste1");
            while(listaDeHostsClient1Obtido.equals("")) {
                Thread.onSpinWait();
            }
            client2.quit();
            while(notificacaoQuitServerObtido.equals("") || notificacaoQuitClient1Obtido.equals("")) {
                Thread.onSpinWait();
            }
            client3 = new Client(new AbstractClientObserver() {
                @Override
                public void listHosts(String[] hosts) {
                    String aux = "";
                    for (String string : hosts) {
                        aux += string+";";
                    }
                    listaDeHostsClient3Obtido = aux;
                }
            }, "127.0.0.1", port, "Teste3");
            while(listaDeHostsClient3Obtido.equals("")) {
                Thread.onSpinWait();
            }
        } catch(IOException ignored) {
        
        }
        assertEquals(notificacaoQuitClient1Esperado, notificacaoQuitClient1Obtido);
        assertEquals(notificacaoQuitServerEsperado, notificacaoQuitServerObtido);
        assertEquals(listaDeHostsClient1Esperado, listaDeHostsClient1Obtido);
        assertEquals(listaDeHostsClient3Esperado, listaDeHostsClient3Obtido);
    }
}
