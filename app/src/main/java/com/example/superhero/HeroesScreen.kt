package com.example.superhero

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.superhero.model.Hero
import com.example.superhero.model.HeroesRepository
import com.example.superhero.ui.theme.SuperheroesTheme

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HeroesList(
    hero: List<Hero> = HeroesRepository.heroes,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onItemClick: (Hero) -> Unit
) {
    val visibleState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    AnimatedVisibility(
        visibleState = visibleState,
        enter = fadeIn(animationSpec = spring(dampingRatio = DampingRatioLowBouncy)),
        exit = fadeOut(),
        modifier = modifier
    ) {
        LazyColumn(contentPadding = contentPadding) {
            itemsIndexed(hero) { index, it ->
                HeroColumnCard(
                    nameRes = it.nameRes,
                    descriptionRes = it.descriptionRes,
                    imageRes = it.imageRes,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { onItemClick(it) }
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = spring(
                                    stiffness = StiffnessVeryLow,
                                    dampingRatio = DampingRatioLowBouncy
                                ),
                                initialOffsetY = { it * (index + 1) }
                            )
                        )
                )
            }
        }
    }
}

@Composable
fun HeroesLazyRow(
    heroes: List<Hero> = HeroesRepository.heroes,
    onItemClick: (Hero) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(heroes) { hero ->
            HeroRowCard(hero = hero, onClick = { onItemClick(hero) })
        }
    }
}

@Composable
fun HeroRowCard(
    hero: Hero,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .width(150.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(hero.nameRes),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(hero.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(top = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(hero.descriptionRes),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun HeroColumnCard(
    modifier: Modifier = Modifier,
    nameRes: Int,
    descriptionRes: Int,
    imageRes: Int
) {
    Card(
        elevation = CardDefaults.cardElevation(2.dp),
        modifier = modifier,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .sizeIn(minHeight = 72.dp)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(nameRes),
                    style = MaterialTheme.typography.displaySmall
                )
                Text(
                    text = stringResource(descriptionRes),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Spacer(Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(shape = MaterialTheme.shapes.small)
            ) {
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = null,
                    alignment = Alignment.TopCenter,
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }
}

@Composable
fun HeroesLazyGrid(
    heroes: List<Hero> = HeroesRepository.heroes,
    onItemClick: (Hero) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(heroes) { hero ->
            HeroGridCard(hero = hero, onClick = { onItemClick(hero) })
        }
    }
}

@Composable
fun HeroGridCard(
    hero: Hero,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(hero.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(bottom = 8.dp)
            )
            Text(
                text = stringResource(hero.nameRes),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview("Light Theme")
@Composable
fun HeroPreview(
    nameRes: Int = R.string.hero1,
    descriptionRes: Int = R.string.description1,
    drawableRes: Int = R.drawable.android_superhero1
) {
    SuperheroesTheme {
        HeroColumnCard(
            nameRes = nameRes,
            descriptionRes = descriptionRes,
            imageRes = drawableRes
        )
    }
}

@Composable
fun Screen1(onItemClick: (Hero) -> Unit) {
    Column {
        HeroesLazyRow(heroes = HeroesRepository.heroes, onItemClick = onItemClick)
        Spacer(modifier = Modifier.height(16.dp))
        HeroesList(hero = HeroesRepository.heroes, onItemClick = onItemClick)
    }
}

@Composable
fun Screen2(onItemClick: (Hero) -> Unit) {
    HeroesLazyGrid(heroes = HeroesRepository.heroes, onItemClick = onItemClick)
}

@Composable
fun Screen3() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.naufal),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 16.dp)
        )
        Text("Naufal Fadhilah Ardhani")
        Text("naufalxyz@gmail.com")
        Text("Politeknik Negeri Jakarta")
        Text("Teknik Informatika")
    }
}

@Preview("Heroes List")
@Composable
fun PreviewHeroesScreen() {
    SuperheroesTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {

        }
    }
}
