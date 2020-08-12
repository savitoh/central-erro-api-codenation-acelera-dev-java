package com.github.savitoh.centralerroapi.usuario;

import com.github.savitoh.centralerroapi.usuario.payload.UsuarioResponsePayload;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USUARIO_SEQ")
    @SequenceGenerator(name="USUARIO_SEQ", sequenceName = "USUARIO_SEQ", allocationSize=1)
    @Column(name = "usuario_id")
    private Integer id;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String nome;

    @NotBlank
    @Size(min = 4, max = 12)
    @Column(nullable = false, length = 12, unique = true)
    private String login;

    @Size(max = 68)
    @NotBlank
    @Column(nullable = false, length = 60)
    private String password;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp")
    private LocalDateTime createdAt;

    /**
     * @deprecated(usado apeanas pelo hibernate)
     */
    @Deprecated(since = "26/07/2020", forRemoval = false)
    public Usuario() {
    }

    public Usuario(@NotBlank @Size(max = 50) String nome,
                   @Size(min = 4, max = 12) @NotBlank String login,
                   @Size(max = 68) @NotBlank String password,
                   PasswordEncoder passwordEncoder) {
        this.nome = nome;
        this.login = login;
        this.password = passwordEncoder.encode(password);
    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public UsuarioResponsePayload toDetalheUserResponsePayload() {
        return new UsuarioResponsePayload(nome, login);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id) &&
                nome.equals(usuario.nome) &&
                login.equals(usuario.login) &&
                Objects.equals(createdAt, usuario.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, login, createdAt);
    }

}