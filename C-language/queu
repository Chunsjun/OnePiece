#include    "jack.h"

/* a simple queu of particles */
static particle    *Q;
static int	    queu_position, queu_size;

void initialize_queu(int size) {
    queu_size = size;
    Q = (particle *) malloc(size * sizeof(particle));
    queu_position = 0;
}

void reset_queu() {
    queu_position = 0;
}

void queu_particle(particle *P) {
    particle *R;
    if (queu_position >= queu_size) {
	fprintf(stderr, "ran out of particle queu space\n");
	exit(-1);
    }
    R = &(Q[queu_position]);
    ++queu_position;
/* cope the particle data over */
    R->type = P->type;
    R->pos[0] = P->pos[0];
    R->pos[1] = P->pos[1];
    R->pos[2] = P->pos[2];
    R->dir[0] = P->dir[0];
    R->dir[1] = P->dir[1];
    R->dir[2] = P->dir[2];
    R->energy = P->energy;
    R->to_elastic = P->to_elastic;
    R->to_inelastic = P->to_inelastic;
}

particle *next_particle() {
    if (queu_position > 0) {
	--queu_position;
	return( &(Q[queu_position]) );
    } else {
	return((particle *) NULL);
    }
}

int empty() {
    return(queu_position == 0);
}
