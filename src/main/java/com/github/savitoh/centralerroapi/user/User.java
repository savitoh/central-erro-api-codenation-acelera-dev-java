package com.github.savitoh.centralerroapi.user;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQUENCE")
    @SequenceGenerator(name="USER_SEQUENCE", sequenceName = "USER_SEQ", allocationSize=1)
    @Column(name = "user_id")
    private Integer id;

    @NotBlank
    @Size(max = 50)
    @Column(nullable = false, length = 50)
    private String nome;

    @Size(min = 4, max = 12)
    @NotBlank
    @Column(nullable = false, length = 12)
    private String login;

    @Size(max = 68)
    @NotBlank
    @Column(nullable = false, length = 68)
    private String password;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp")
    private LocalDateTime createdAt;

    /**
     * @deprecated(usado apeanas pelo hibernate)
     */
    @Deprecated(since = "26/07/2020", forRemoval = false)
    public User() {
    }

    public User(@NotBlank @Size(max = 50) String nome,
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

    public UserResponsePayload toDetalheUserResponsePayload() {
        return new UserResponsePayload(nome, login);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((login == null) ? 0 : login.hashCode());
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (login == null) {
            if (other.login != null)
                return false;
        } else if (!login.equals(other.login))
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        return true;
    }



    
}