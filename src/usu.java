import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class usu {
    private JTextField usua;
    private JTextField passwaord;
    private JButton guardar;
    private JPanel iniciarS;

    private List<Usuario> usuarios2 = new ArrayList<>();
    private String[] usuarios = new String[5];
    private String[] contrasenas = new String[5];
    private int contador = 0;


    public usu() {
        cargarUsuariosDesdeArchivo();

        guardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usuario = usua.getText();
                String contrasena = passwaord.getText();
                if (contador < 5) {
                    usuarios[contador] = usuario;
                    contrasenas[contador] = contrasena;
                    contador++;
                    guardarUsuariosEnArchivo();


                    // Limpiar los campos de usuario y contraseña después de guardarlos
                    usua.setText("");
                    passwaord.setText("");
                }

                if (validarCredenciales(usuario, contrasena)) {
                    mostrarFormularioUsuario(usuario);
                } else {
                    JOptionPane.showMessageDialog(iniciarS, "Credenciales inválidas. Intente nuevamente.", "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Limpiar los campos de usuario y contraseña después de intentar el inicio de sesión
                usua.setText("");
                passwaord.setText("");
            }
        });
    }
    private void guardarUsuariosEnArchivo() {
        try {
            FileWriter fileWriter = new FileWriter("usuarios.dat");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (int i = 0; i < 5; i++) {
                bufferedWriter.write("Usuario: " + usuarios[i] + " - Contraseña: " + contrasenas[i]);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            fileWriter.close();

            System.out.println("Usuarios y contraseñas guardados correctamente en el archivo.");
        } catch (IOException e) {
            System.out.println("Error al guardar los usuarios y contraseñas: " + e.getMessage());
        }
    }


    private void cargarUsuariosDesdeArchivo() {
        try {
            FileInputStream fis = new FileInputStream("usuarios.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);

            usuarios2.clear();
            while (fis.available() > 0) {
                Usuario usuarioGuardado = (Usuario) ois.readObject();
                usuarios2.add(usuarioGuardado);
            }

            ois.close();
        } catch (Exception e) {
            System.out.println("Error al cargar los usuarios desde el archivo: " + e.getMessage());
        }
    }

    private boolean validarCredenciales(String usuario, String contrasena) {
        for (Usuario u : usuarios2) {
            if (u.getNombreUsuario().equals(usuario) && u.getContrasena().equals(contrasena)) {
                return true;
            }
        }
        return false;
    }

    private void mostrarFormularioUsuario(String usuario) {
        JFrame frame = new JFrame("Mostrar Usuario");
        JLabel labelUsuario = new JLabel("Bienvenido, " + usuario + "!");
        frame.setContentPane(labelUsuario);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar en la pantalla
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Iniciar Sesión");
        frame.setContentPane(new usu().iniciarS);
        frame.setBounds(650, 300, 0, 0);
        frame.pack();
        frame.setVisible(true);
    }
}

