package main;

public class CollusionChecker {

    GamePanel gp;

    public CollusionChecker(GamePanel gp) {
        this.gp = gp;
    }
    //collusion rectangle
    public boolean checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width ;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX /gp.tileSize;
        int entityRightCol = entityRightWorldX / gp.tileSize;
        int entityTopRow = entityTopWorldY / gp.tileSize;
        int entityBottomRow = entityBottomWorldY / gp.tileSize;

        int tileIndex1 , tileIndex2 ;
        switch (entity.direction){
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileIndex1 = gp.wallM.mapTileNum[entityTopRow][entityLeftCol];
                tileIndex2 = gp.wallM.mapTileNum[entityTopRow][entityRightCol];
                if(gp.wallM.walls[tileIndex1].collision || gp.wallM.walls[tileIndex2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileIndex1 = gp.wallM.mapTileNum[entityBottomRow][entityLeftCol];
                tileIndex2 = gp.wallM.mapTileNum[entityBottomRow][entityRightCol];
                if (gp.wallM.walls[tileIndex1].collision || gp.wallM.walls[tileIndex2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileIndex1 = gp.wallM.mapTileNum[entityTopRow][entityLeftCol];
                tileIndex2 = gp.wallM.mapTileNum[entityBottomRow][entityLeftCol];
                if(gp.wallM.walls[tileIndex1].collision || gp.wallM.walls[tileIndex2].collision){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tileIndex1 = gp.wallM.mapTileNum[entityTopRow][entityRightCol];
                tileIndex2 = gp.wallM.mapTileNum[entityBottomRow][entityRightCol];
                if(gp.wallM.walls[tileIndex1].collision || gp.wallM.walls[tileIndex2].collision){
                    entity.collisionOn = true;
                }
                break;
        }

        return false;
    }

    public boolean isLeftExplodable(int bombX, int bombY, int length){
        int col = (bombX - length) / gp.tileSize;
        int row = bombY / gp.tileSize;
        if (col < 0 || col >= gp.maxWorldCol || row < 0 || row >= gp.maxWorldRow) {
            return false;
        }
        int tileIndex = gp.wallM.mapTileNum[row][col];
        return !gp.wallM.walls[tileIndex].collision;

    }
    public boolean isBottomExplodable(int bombX, int bombY, int length){
        int col = bombX / gp.tileSize;
        int row = (bombY + length) / gp.tileSize;
        if (col < 0 || col >= gp.maxWorldCol || row < 0 || row >= gp.maxWorldRow) {
            return false;
        }
        int tileIndex = gp.wallM.mapTileNum[row][col];
        return !gp.wallM.walls[tileIndex].collision;

    }
    public boolean isTopExplodable(int bombX, int bombY, int length){
        int col = bombX / gp.tileSize;
        int row = (bombY - length) / gp.tileSize;
        if (col < 0 || col >= gp.maxWorldCol || row < 0 || row >= gp.maxWorldRow) {
            return false;
        }
        int tileIndex = gp.wallM.mapTileNum[row][col];
        return !gp.wallM.walls[tileIndex].collision;
    }
    public boolean isRightExplodable(int bombX, int bombY, int length){
        int col = (bombX + length) / gp.tileSize;
        int row = bombY / gp.tileSize;
        if (col < 0 || col >= gp.maxWorldCol || row < 0 || row >= gp.maxWorldRow) {
            return false;
        }
        int tileIndex = gp.wallM.mapTileNum[row][col];
        return !gp.wallM.walls[tileIndex].collision;
    }
}
